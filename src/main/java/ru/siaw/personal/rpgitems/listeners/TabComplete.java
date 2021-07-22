package ru.siaw.personal.rpgitems.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.siaw.personal.rpgitems.utilities.Lore;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
    private final Lore lore = new Lore();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rpg")) {
            List<String> completes = new ArrayList<>();
            if (sender.hasPermission("rpg.use") || sender.hasPermission("rpg.admin")) {
                if (args.length == 1) {
                    completes.add("Функции плагина");
                    if (sender.hasPermission("rpg.add") || sender.hasPermission("rpg.use") || sender.hasPermission("rpg.admin")) {
                        completes.add("add");
                    }
                    if (sender.hasPermission("rpg.remove") || sender.hasPermission("rpg.use") || sender.hasPermission("rpg.admin")) {
                        completes.add("remove");
                    }
                    if (sender.hasPermission("rpg.list") || sender.hasPermission("rpg.use") || sender.hasPermission("rpg.admin")) {
                        completes.add("list");
                    }
                } else if (args.length == 2) {
                    completes.add("Навык");
                    ItemStack itemInHand = ((Player) sender).getInventory().getItemInMainHand();
                    List<String> skills = new ArrayList<>(lore.getSkills());
                    ArrayList<String> lores = new ArrayList<>(lore.getLores(itemInHand));
                    if (args[0].equalsIgnoreCase("add")) {
                        if (sender.hasPermission("rpg.add") || sender.hasPermission("rpg.use") || sender.hasPermission("rpg.admin")) {
                            completes.addAll(skills);
                            for (String l : lores) {
                                if (l != null) {
                                    completes.remove(lore.decodeLore(l));
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (sender.hasPermission("rpg.remove") || sender.hasPermission("rpg.use") || sender.hasPermission("rpg.admin")) {
                            for (String l : lores) {
                                if (l != null) {
                                    completes.add(lore.decodeLore(l));
                                }
                            }
                            if (completes.size() == 1) {
                                completes.addAll(skills);
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("list")) {
                        return null;
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (sender.hasPermission("rpg.add") || sender.hasPermission("rpg.use") || sender.hasPermission("rpg.admin")) {
                            completes.add("Шанс (1-100)");
                        }
                    }
                }
                return completes;
            }
            return null;
        }
        return null;
    }
}
