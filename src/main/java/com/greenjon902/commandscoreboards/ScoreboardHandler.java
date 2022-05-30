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

public class ScoreboardHandler {
    private final HashMap<UUID, ScoreboardController> playerScoreboardData = new HashMap<>();
    private final ScoreboardController defaultPlayerScoreboard;

    private final File dataFolder;
    private final File playerScoreboardsFolder;
    private final File defaultScoreboardFile;

    public ScoreboardHandler(CommandScoreboards commandScoreboards) {
        dataFolder = commandScoreboards.getDataFolder();
        playerScoreboardsFolder = new File(dataFolder, "playerScoreboards");
        defaultScoreboardFile = new File(dataFolder, "defaultScoreboard.txt");

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        if (!playerScoreboardsFolder.exists()) {
            playerScoreboardsFolder.mkdirs();
        }

        try {

            if (!defaultScoreboardFile.exists()) {
                FileWriter fileWriter = new FileWriter(defaultScoreboardFile);
                fileWriter.write("Example Title\nIt can hold 15 lines\n\n\n\n\n\n\n\n\n\nscoreboard\nexample\nan\nis\nThis");
                fileWriter.close();
            }
            Scanner fileScanner = new Scanner(defaultScoreboardFile).useDelimiter("\n");
            String title = fileScanner.next();
            ArrayList<String> lines = new ArrayList<>();
            while (fileScanner.hasNext()) {
                lines.add(fileScanner.next());
            }
            defaultPlayerScoreboard = new ScoreboardController(Component.text(title), lines.toArray(new String[0]));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Scoreboard loadScoreboard(UUID playerId) {
        if (playerScoreboardData.containsKey(playerId)) {
            return playerScoreboardData.get(playerId).getScoreboard();
        }


        File file = new File(playerScoreboardsFolder, playerId.toString() + ".txt");
        if (file.exists()) {
            try {
                Scanner fileScanner = new Scanner(defaultScoreboardFile).useDelimiter("\n");

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

        playerScoreboardData.put(playerId, defaultPlayerScoreboard.clone());
        return defaultPlayerScoreboard.getScoreboard();
    }
}

class ScoreboardController {
    private final Score[] scores;
    private final String[] scoreNames;
    private final Scoreboard scoreboard;
    private final Objective objective;

    public ScoreboardController(Component displayName, String[] lines) {
        scoreNames =  new String[15];
        scores = new Score[15];

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("data", "dummy", displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (!(lines.length == 15)) {
            throw new IllegalArgumentException("Scoreboard controller takes a line length of 15 only");
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

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }

    public ScoreboardController clone() {
        return new ScoreboardController(objective.displayName(), scoreNames);
    }
}