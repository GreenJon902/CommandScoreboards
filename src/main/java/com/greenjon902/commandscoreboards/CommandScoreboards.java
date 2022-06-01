package com.greenjon902.commandscoreboards;

import com.greenjon902.commandscoreboards.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandScoreboards extends JavaPlugin {
    private static ScoreboardHandler scoreboardHandler;

    public static ScoreboardHandler getScoreboardHandler() {
        return scoreboardHandler;
    }

    @Override
    public void onEnable() {
        scoreboardHandler = new ScoreboardHandler(this);

        getCommand("editplayerscoreboard").setExecutor(new EditPlayerScoreboardCommand());
        getCommand("editplayerscoreboardtitle").setExecutor(new EditPlayerScoreboardTitleCommand());
        getCommand("resetplayerscoreboard").setExecutor(new ResetPlayerScoreboard());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
