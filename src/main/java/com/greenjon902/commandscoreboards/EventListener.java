package com.greenjon902.commandscoreboards;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(
                CommandScoreboards.getScoreboardHandler().makePlayerScoreboard(
                    event.getPlayer().getUniqueId(),
                    Component.text("Scoreboard")));
    }
}
