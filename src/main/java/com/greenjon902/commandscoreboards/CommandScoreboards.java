package com.greenjon902.commandscoreboards;

import com.greenjon902.commandscoreboards.commands.EditGlobalScoreboardCommand;
import com.greenjon902.commandscoreboards.commands.EditGlobalScoreboardTitleCommand;
import com.greenjon902.commandscoreboards.commands.EditPlayerScoreboardCommand;
import com.greenjon902.commandscoreboards.commands.EditPlayerScoreboardTitleCommand;
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
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
