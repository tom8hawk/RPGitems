package ru.siaw.personal.rpgitems.listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.siaw.personal.rpgitems.utilities.FileManager;
import ru.siaw.personal.rpgitems.utilities.Lore;

import java.util.Objects;

public class Commands implements CommandExecutor {
    private final Lore lore = new Lore();

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rpg")) {
            if (sender.hasPermission("rpg.use") || sender.hasPermission("rpg.admin") || sender.isOp()) {
                if (sender instanceof Player) {
                    if (args.length != 0) {
                        switch (args[0].toLowerCase()) {
                            case "list":
                                if (args.length == 1) {
                                    if (sender.hasPermission("rpg.list") && sender.hasPermission("rpg.admin") && sender.isOp()) {
                                        ItemStack itemInHand = ((Player) sender).getInventory().getItemInMainHand();
                                        String loresList = lore.loresList(lore.getLores(itemInHand));
                                        if (loresList != null) {
                                            sender.sendMessage(loresList);
                                        } else {
                                            message(sender, "noSkills");
                                        }
                                    } else {
                                        message(sender, "permission");
                                    }
                                } else {
                                    message(sender, "incorrectUse");
                                }
                                return false;
                            case "add":
                                if (args.length == 3) {
                                    if (sender.hasPermission("rpg.add") || sender.hasPermission("rpg.admin") || sender.isOp()) {
                                        if (Byte.parseByte(args[2]) >= 1 && Byte.parseByte(args[2]) <= 100) {
                                            lore.addLore((Player) sender, args[1], args[2]);
                                        }
                                    } else {
                                        message(sender, "permission");
                                    }
                                } else {
                                    message(sender, "incorrectUse");
                                }
                                return false;
                            case "remove":
                                if (args.length == 2) {
                                    if (sender.hasPermission("rpg.remove") && sender.hasPermission("rpg.admin") && sender.isOp()) {
                                        lore.removeLore((Player) sender, args[1]);
                                    } else {
                                        message(sender, "permission");
                                    }
                                } else {
                                    message(sender, "incorrectUse");
                                }
                                return false;
                        }
                        message(sender, "incorrectUse");
                    }
                } else {
                    message(sender, "notPlayer");
                }
            } else {
                message(sender, "permission");
            }
        }
        return false;
    }

    private void message(CommandSender sender, String msg) {
        switch (msg) {
            case "permission":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(FileManager.getMsg().getString("permission"))));
                break;
            case "notPlayer":
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(FileManager.getMsg().getString("notPlayer"))));
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
        }
    }
}
