package com.greenjon902.commandscoreboards;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class ScoreboardHandler {
    private final HashMap<UUID, ScoreboardController> playerScoreboardData = new HashMap<>();

    private final File dataFolder;
    private final File playerScoreboardsFolder;

    private final Logger logger;

    public ScoreboardHandler(CommandScoreboards commandScoreboards) {
        logger = commandScoreboards.getLogger();

        dataFolder = commandScoreboards.getDataFolder();
        playerScoreboardsFolder = new File(dataFolder, "playerScoreboards");

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        if (!playerScoreboardsFolder.exists()) {
            playerScoreboardsFolder.mkdirs();
        }
    }

    public Scoreboard loadScoreboard(UUID playerId) {
        logger.info("Loading scoreboard for " + playerId + " (" + Bukkit.getOfflinePlayer(playerId).getName() + ")");

        File file = new File(playerScoreboardsFolder, playerId + ".txt");
        if (file.exists()) {
            try {
                Scanner fileScanner = new Scanner(file).useDelimiter("\n");

                String title = fileScanner.next();
                ArrayList<String> lines = new ArrayList<>();
                while (fileScanner.hasNext()) {
                    lines.add(fileScanner.next());
                }
                ScoreboardController scoreboardController = new ScoreboardController(Component.text(title), lines.toArray(new String[0]));
                playerScoreboardData.put(playerId, scoreboardController);
                return scoreboardController.getScoreboard();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        logger.info("No scoreboard found, making new one");

        playerScoreboardData.put(playerId, ScoreboardController.newEmptyScoreboardController());
        return playerScoreboardData.get(playerId).getScoreboard();
    }

    public void unloadScoreboard(UUID playerId) {
        logger.info("Unloading Scoreboard for " + playerId + " (" + Bukkit.getOfflinePlayer(playerId).getName() + ")");

        if (!playerScoreboardData.containsKey(playerId)) {
            throw new IllegalArgumentException("Player was not loaded");
        }

        StringBuilder data = new StringBuilder();
        data.append(playerScoreboardData.get(playerId).getObjective().displayName());
        data.append("\n");
        for (String line : playerScoreboardData.get(playerId).getScoreNames()) {
            data.append(line);
            data.append("\n");
        }
        String dataString = data.substring(0, data.length()-1);

        File file = new File(playerScoreboardsFolder, playerId + ".txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(dataString);
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class ScoreboardController {
    private final Score[] scores;
    private final String[] scoreNames;
    private final Scoreboard scoreboard;
    private final Objective objective;

    public ScoreboardController(Component displayName, String[] lines) {
        scoreNames =  new String[lines.length];
        scores = new Score[lines.length];

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("data", "dummy", displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (lines.length > 15) {
            throw new IllegalArgumentException("Scoreboard controller takes a maximum line length of 15");
        }
        for (int i=0; i<lines.length; i++) {
            String line = lines[i];
            while (Arrays.asList(scoreNames).contains(line)) {
                line = line + " ";
            }

            Score score = objective.getScore(line);
            score.setScore(i);
            scores[i] = score;
            scoreNames[i] = line;
        }
    }

    public static ScoreboardController newEmptyScoreboardController() {
        return new ScoreboardController(Component.text(""), new String[] {}); //Make empty list
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }

    public String[] getScoreNames() {
        return scoreNames.clone();
    }
}