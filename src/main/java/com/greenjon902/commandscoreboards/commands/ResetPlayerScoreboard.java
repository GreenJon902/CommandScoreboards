package com.greenjon902.commandscoreboards.commands;

import net.kyori.adventure.text.SelectorComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResetPlayerScoreboard implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "/resetplayerscoreboard takes 2 arguments.");
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "Player not found.");
            return true;
        }

        return true;
    }
}
