package com.greenjon902.commandscoreboards;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardHandler {
    private final HashMap<UUID, Score[]> playerLines = new HashMap<>();

    public @NotNull Scoreboard makePlayerScoreboard(UUID playerUUID, Component displayName) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(playerUUID.toString(), "dummy", displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        playerLines.put(playerUUID, new Score[15]);

        for (int i=0; i<15; i++) {
            Score line = objective.getScore(String.format("%1$"+i+"s", ""));
            line.setScore(i);
            playerLines.get(playerUUID)[i] = line;
        }
        return scoreboard;
    }
}
