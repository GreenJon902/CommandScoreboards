package com.greenjon902.commandscoreboards.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EditPlayerScoreboardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 3) {
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "/editplayerscoreboard takes 2 arguments.");
        }

        int lineNumber;
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "Player not found.");
            return true;
        }
        try {
            lineNumber = Integer.parseInt(args[1]);
            if (!(0 < lineNumber && lineNumber < 15)) {
                throw new NumberFormatException("Number must be between 0 and 14");
            }
        } catch (NumberFormatException exception) {
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "Invalid line number.");
            return true;
        }

        return true;
    }
}
