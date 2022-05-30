package com.greenjon902.commandscoreboards.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EditGlobalScoreboardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "/editglobalscoreboard takes 2 arguments.");
        }

        int lineNumber;
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
