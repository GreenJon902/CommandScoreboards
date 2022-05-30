package com.greenjon902.commandscoreboards;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class ScoreboardHandler {
    private final HashMap<UUID, ScoreboardController> playerScoreboardData = new HashMap<>();
    private final ScoreboardController defaultPlayerScoreboard;

    public ScoreboardHandler(CommandScoreboards commandScoreboards) {
        File dataFolder = commandScoreboards.getDataFolder();
        File defaultScoreboardFile = new File(dataFolder, "defaultScoreboard.txt");

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
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
            System.out.println(defaultScoreboardFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Scoreboard loadScoreboard(UUID playerId) {
        if (playerScoreboardData.containsKey(playerId)) {
            return playerScoreboardData.get(playerId).getScoreboard();
        }
        return defaultPlayerScoreboard.getScoreboard(); //TODO: Make new scoreboards
    }
}

class ScoreboardController {
    private final Score[] scores = new Score[15];
    private final String[] scoreNames = new String[15];
    private final Scoreboard scoreboard;
    private final Objective objective;

    public ScoreboardController(TextComponent displayName, String[] lines) {
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
        System.out.println(Arrays.toString(scores));
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }
}