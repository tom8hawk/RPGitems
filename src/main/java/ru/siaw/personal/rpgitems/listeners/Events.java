package ru.siaw.personal.rpgitems.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import ru.siaw.personal.rpgitems.utilities.Lore;
import ru.siaw.personal.rpgitems.utilities.Skills;

import java.util.ArrayList;
import java.util.Random;

public class Events implements Listener {

    private final Lore lore = new Lore();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Player damager = (Player) e.getDamager();
        Entity attacked = e.getEntity();
        ItemStack weapon = damager.getInventory().getItemInMainHand();
        Material weaponType = weapon.getType();
        if (weaponType == Material.IRON_AXE || weaponType == Material.IRON_SWORD || weaponType == Material.GOLDEN_AXE || weaponType == Material.GOLDEN_SWORD ||
                weaponType == Material.DIAMOND_AXE || weaponType == Material.DIAMOND_SWORD || weaponType == Material.NETHERITE_AXE || weaponType == Material.NETHERITE_SWORD) {
            skillSelection(weapon, damager, attacked);
        }
    }

    public void skillSelection(ItemStack weapon, Player damager, Entity attacked) {
        if (weapon.hasItemMeta() && weapon.getItemMeta().hasLore()) {
            ArrayList<String> lores = new ArrayList<>(lore.getLores(weapon));
            ArrayList<Byte> chances = new ArrayList<>();
            ArrayList<String> skillList = new ArrayList<>();
            for (String l : lores) {
                skillList.add(lore.decodeLore(l));
                chances.add(lore.decodeChance(l));
            }
            Skills skills = new Skills();
            for (String s : skillList) {
                for (byte c : chances) {
                    switch (s) {
                        case "arson":
                            if (doSkill(c)) {
                                skills.arsonSkill(attacked);
                            }
                            break;
                        case "bleeding":
                            if (doSkill(c)) {
                                skills.bleedingSkill(attacked);
                            }
                            break;
                        case "hit":
                            if (doSkill(c)) {
                                skills.hitSkill(damager);
                            }
                            break;
                        case "input":
                            if (doSkill(c)) {
                                skills.inputSkill(damager, (Player) attacked);
                            }
                            break;
                        case "lightning":
                            if (doSkill(c)) {
                                skills.lightningSkill(attacked);
                            }
                            break;
                        case "poisoning":
                            if (doSkill(c)) {
                                skills.poisoningSkill((Player) attacked);
                            }
                            break;
                        case "vampirism":
                            if (doSkill(c)) {
                                skills.vampirismSkill(damager);
                            }
                            break;
                        case "wither":
                            if (doSkill(c)) {
                                skills.witherSkill((Player) attacked);
                            }
                            break;
                    }
                }
            }
        }
    }

    public boolean doSkill(byte chance) {
        Random rnd = new Random();
        byte randomNum = (byte) rnd.nextInt(100);
        return randomNum <= chance;
    }
}
