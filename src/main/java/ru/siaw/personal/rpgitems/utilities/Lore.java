package ru.siaw.personal.rpgitems.utilities;

import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Lore {
    private final String[] skill = {"arson", "bleeding", "hit", "lightning", "poisoning", "vampirism", "wither", "input", "shield"};
    private final Messages message = new Messages();

    public Collection<? extends String> getSkills() {
        return Arrays.asList(skill);
    }

    public void addLore(Player sender, String addSkill, String chance) {
        ItemStack stack = sender.getInventory().getItemInMainHand();
        ItemMeta meta = stack.getItemMeta();
        if (!stack.getType().isAir()) {
            if (meta != null) {
                ArrayList<String> lore = new ArrayList<>();
                if (meta.hasLore()) {
                    lore.addAll(meta.getLore());
                }
                for (String s : skill) {
                    if (addSkill.contains(s)) {
                        lore.add(s.charAt(0) + chance);
                    }
                }
                meta.setLore(lore);
                stack.setItemMeta(meta);
                message.msg(sender, "add");
            }
        } else {
            message.msg(sender, "air");
        }
    }

    public ArrayList<String> getLores(ItemStack stack) {
        ArrayList<String> foundLores = new ArrayList<>();
        if (!stack.getType().isAir()) {
            ItemMeta meta = stack.getItemMeta();
            if (meta.hasLore()) {
                ArrayList<String> lore = new ArrayList<>(meta.getLore());
                for (String l : lore) {
                    for (String s : skill) {
                        for (byte chance = 100; chance > -1; chance--) {
                            String toFind = s.charAt(0) + Byte.toString(chance);
                            if (!foundLores.contains(l)) {
                                if (l.contains(toFind)) {
                                    foundLores.add(toFind);
                                }
                            }
                        }
                    }
                }
            }
        }
        return foundLores;
    }

    public void removeLore(Player sender, String removeSkill) {
        ItemStack stack = sender.getInventory().getItemInMainHand();
        ItemMeta meta = stack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        if (!stack.getType().isAir()) {
            if (meta != null) {
                if (meta.hasLore()) {
                    lore.addAll(meta.getLore());
                }
                for (String s : skill) {
                    for (byte chance = 100; chance > -1; chance--) {
                        String toRemove = s.charAt(0) + Byte.toString(chance);
                        if (removeSkill.contains(String.valueOf(s.charAt(0)))) {
                            lore.remove(toRemove);
                        }
                        meta.setLore(lore);
                        sender.getInventory().getItemInMainHand().setItemMeta(meta);
                    }
                }
                message.msg(sender, "remove");
            }
        } else {
            message.msg(sender, "air");
        }
    }

    public void removePacketLore(ItemStack stack, PacketContainer packet) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            return;
        }
        if (!meta.hasLore()) {
            return;
        }
        ArrayList<String> lore = new ArrayList<>(meta.getLore());
        for (String s : skill) {
            for (byte chance = 100; chance > -1; chance--) {
                lore.remove(s.charAt(0) + Byte.toString(chance));
                meta.setLore(lore);
                stack.setItemMeta(meta);
                packet.getItemModifier().write(0, stack);
            }
        }
    }

    public String decodeLore(String lore) {
        String returnLore = null;
        lore = lore.replaceAll("\\d", "");
        for (String s : skill) {
            String symbol = String.valueOf(s.charAt(0));
            if (lore.contains(symbol)) {
                returnLore = s;
            }
        }
        return returnLore;
    }

    public Byte decodeChance(String lore) {
        return Byte.valueOf(lore.replaceAll("\\D", ""));
    }

    public String loresList(ArrayList<String> lores) {
        ArrayList<String> decodedLores = new ArrayList<>();
        ArrayList<Byte> decodedChances = new ArrayList<>();
        StringBuilder msg = new StringBuilder();
        for (String l : lores) {
            if (l != null) {
                decodedLores.add(decodeLore(l));
                decodedChances.add(decodeChance(l));
            }
        }
        byte doMsg = (byte) lores.size();
        while (doMsg > -1) {
            String dl;
            String dc;
            try {
                dl = decodedLores.get(doMsg);
            } catch (Exception e) {
                dl = null;
            }
            try {
                dc = String.valueOf(decodedChances.get(doMsg));
            } catch (Exception e) {
                dc = null;
            }
            if (dl != null && dc != null) {
                if (msg.length() == 0) {
                    msg.append(ChatColor.GOLD + "Навыки на инструменете: " + ChatColor.YELLOW).append(dl).append(" ").append(dc);
                } else {
                    msg.append(ChatColor.GOLD + ", " + ChatColor.YELLOW).append(dl).append(" ").append(dc);
                }
            }
            doMsg--;
        }
        return msg.toString();
    }
}

