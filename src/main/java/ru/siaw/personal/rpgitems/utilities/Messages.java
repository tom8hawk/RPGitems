package ru.siaw.personal.rpgitems.utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class Messages {
    public void msg(CommandSender sender, String msg) {
        switch (msg) {
            case "permission":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(FileManager.getMsg().getString("permission"))));
                break;
            case "air":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(FileManager.getMsg().getString("air"))));
                break;
            case "incorrectUse":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(FileManager.getMsg().getString("incorrectUse"))));
                break;
            case "alreadyHas":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(FileManager.getMsg().getString("alreadyHas"))));
                break;
            case "noSkills":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(FileManager.getMsg().getString("noSkills"))));
                break;
            case "add":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(FileManager.getMsg().getString("add"))));
                break;
            case "remove":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(FileManager.getMsg().getString("remove"))));
                break;
        }
    }
}
