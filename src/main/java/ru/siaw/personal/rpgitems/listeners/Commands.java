package ru.siaw.personal.rpgitems.listeners;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ru.siaw.personal.rpgitems.utilities.Messages;
import ru.siaw.personal.rpgitems.utilities.Lore;
import org.bukkit.command.CommandExecutor;

public class Commands implements CommandExecutor {
    final Lore lore;
    final Messages message;

    public Commands() {
        lore = new Lore();
        message = new Messages();
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rpg")) {
            if (sender.hasPermission("rpg.use") || sender.hasPermission("rpg.admin") || sender.isOp()) {
                if (sender instanceof Player) {
                    if (args.length != 0) {
                        String lowerCase = args[0].toLowerCase();
                        switch (lowerCase) {
                            case "list":
                                if (args.length == 1) {
                                    if (sender.hasPermission("rpg.list") && sender.hasPermission("rpg.admin") && sender.isOp()) {
                                        ItemStack itemInHand = ((Player) sender).getInventory().getItemInMainHand();
                                        if (!itemInHand.getType().isAir()) {
                                            String loresList = lore.loresList(lore.getLores(itemInHand));
                                            if (loresList.length() != 0) {
                                                sender.sendMessage(loresList);
                                            } else {
                                                message.msg(sender, "noSkills");
                                            }
                                        } else {
                                            message.msg(sender, "air");
                                        }
                                    } else {
                                        message.msg(sender, "permission");
                                    }
                                } else {
                                    message.msg(sender, "incorrectUse");
                                }
                                return false;
                            case "add":
                                if (args.length == 3) {
                                    if (sender.hasPermission("rpg.add") || sender.hasPermission("rpg.admin") || sender.isOp()) {
                                        if (Byte.parseByte(args[2]) > 0 && Byte.parseByte(args[2]) < 101) {
                                            lore.addLore((Player) sender, args[1], args[2]);
                                        } else {
                                            message.msg(sender, "incorrectUse");
                                        }
                                    } else {
                                        message.msg(sender, "permission");
                                    }
                                } else {
                                    message.msg(sender, "incorrectUse");
                                }
                                return false;
                            case "remove":
                                if (args.length == 2) {
                                    if (sender.hasPermission("rpg.remove") && sender.hasPermission("rpg.admin") && sender.isOp()) {
                                        lore.removeLore((Player) sender, args[1]);
                                    } else {
                                        message.msg(sender, "permission");
                                    }
                                } else {
                                    message.msg(sender, "incorrectUse");
                                }
                                return false;
                            default:
                                message.msg(sender, "incorrectUse");
                                break;
                        }
                    }
                } else {
                    message.msg(sender, "notPlayer");
                }
            } else {
                message.msg(sender, "permission");
            }
        }
        return false;
    }
}
