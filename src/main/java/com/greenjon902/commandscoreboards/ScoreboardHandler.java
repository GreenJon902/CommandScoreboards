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
                ScoreboardController scoreboardController = new ScoreboardController(title, lines.toArray(new String[0]));
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

    public void setScoreboardLine(UUID playerId, int line, String value) {
        playerScoreboardData.get(playerId).setLine(line, value);
    }
}

class ScoreboardController {
    private String displayName;
    private String[] scoreNames;
    private final Scoreboard scoreboard;
    private Objective objective;

    public ScoreboardController(String displayName, String[] lines) {
        this.displayName = displayName;

        scoreNames =  lines;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        if (lines.length > 15) {
            throw new IllegalArgumentException("Scoreboard controller takes a maximum line length of 15");
        }

        updateScoreboard();
    }

    public static ScoreboardController newEmptyScoreboardController() {
        return new ScoreboardController("", new String[] {}); //Make empty list
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

    public void setLine(int line, String value) {
        if (line < 0 || line > 15) {
            throw new IllegalArgumentException("Scoreboard's have up to 14 lines, starting at 0");
        }

        if ((scoreNames.length - 1) < line) {
            String[] scoreNames2 = new String[line + 1];
            System.arraycopy(scoreNames, 0, scoreNames2, 0, scoreNames.length);
            scoreNames = scoreNames2;

            for (int i=0; i<scoreNames.length; i++) {
                String scoreName = scoreNames[i];

                if (scoreName == null) {
                    scoreName = "";
                }

                scoreNames[i] = scoreName;
            }
        }

        scoreNames[line] = value;
        updateScoreboard();
    }

    public void updateScoreboard() {
        if (scoreboard.getObjective("data") != null) {
            objective.unregister();
        }

        objective = scoreboard.registerNewObjective("data", "dummy", displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        ArrayList<String> alreadyScores = new ArrayList<>(); // list of score names that there already are bc can't be duplicates
        for (int i=0; i<scoreNames.length; i++) {
            String line = scoreNames[i];
            while (alreadyScores.contains(line)) {
                line = line + " ";
            }

            Score score = objective.getScore(line);
            score.setScore(i);
            alreadyScores.add(line);
        }
    }
}