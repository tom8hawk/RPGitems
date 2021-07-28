package ru.siaw.personal.rpgitems.utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class Messages {
    public void msg(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(FileManager.getMsg().getString(msg))));
    }
}
