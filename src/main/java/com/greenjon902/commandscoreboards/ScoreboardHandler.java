package com.greenjon902.commandscoreboards;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardHandler {
    private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private final HashMap<UUID, Score[]> playerLines = new HashMap<>();

    public void makePlayerScoreboard(UUID playerUUID, Component displayName) {
        Objective objective = scoreboard.registerNewObjective(playerUUID.toString(), "dummy", displayName);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (int i=0; i<15; i++) {
            Score line = objective.getScore("");
            line.setScore(i);
            playerLines.get(playerUUID)[i] = line;
        }
    }
}
