package com.greenjon902.commandscoreboards.commands;

import com.greenjon902.commandscoreboards.CommandScoreboards;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EditPlayerScoreboardTitleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "/editplayerscoreboardtitle takes 2 arguments.");
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "Player not found.");
            return true;
        }

        CommandScoreboards.getScoreboardHandler().setScoreboardTitle(player.getUniqueId(), args[1]);
        return true;
    }
}
