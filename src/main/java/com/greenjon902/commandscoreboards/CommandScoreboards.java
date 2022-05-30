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
        scoreboardHandler = new ScoreboardHandler();

        getCommand("editglobalscoreboard").setExecutor(new EditGlobalScoreboardCommand());
        getCommand("editglobalscoreboardtitle").setExecutor(new EditGlobalScoreboardTitleCommand());
        getCommand("editplayerscoreboard").setExecutor(new EditPlayerScoreboardCommand());
        getCommand("editplayerscoreboardtitle").setExecutor(new EditPlayerScoreboardTitleCommand());
        getCommand("editdefaultplayerscoreboard").setExecutor(new EditDefaultPlayerScoreboardCommand());
        getCommand("editdefaultplayerscoreboardtitle").setExecutor(new EditDefaultPlayerScoreboardTitleCommand());
        getCommand("resetplayerscoreboard").setExecutor(new ResetPlayerScoreboard());
        getCommand("switchtoglobalscoreboard").setExecutor(new SwitchToGlobalScoreboard());
        getCommand("switchtoplayerscoreboard").setExecutor(new SwitchToPlayerScoreboard());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
