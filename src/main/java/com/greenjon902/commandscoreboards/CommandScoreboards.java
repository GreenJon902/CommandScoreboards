package com.greenjon902.commandscoreboards;

import org.bukkit.plugin.java.JavaPlugin;

public final class CommandScoreboards extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("editscoreboard").setExecutor(new EditScoreboardCommand());
        System.out.println((getCommand("tellraw")));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
