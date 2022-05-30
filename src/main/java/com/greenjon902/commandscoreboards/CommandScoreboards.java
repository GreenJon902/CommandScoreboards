package com.greenjon902.commandscoreboards;

import org.bukkit.plugin.java.JavaPlugin;

public final class CommandScoreboards extends JavaPlugin {
    private static ScoreboardHandler scoreboardHandler;

    public static ScoreboardHandler getScoreboardHandler() {
        return scoreboardHandler;
    }

    @Override
    public void onEnable() {
        scoreboardHandler = new ScoreboardHandler();

        getCommand("editscoreboard").setExecutor(new EditScoreboardCommand());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
