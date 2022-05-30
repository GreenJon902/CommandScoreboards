package com.greenjon902.commandscoreboards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EditScoreboardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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
