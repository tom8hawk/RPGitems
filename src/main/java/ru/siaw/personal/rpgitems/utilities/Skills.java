package ru.siaw.personal.rpgitems.utilities;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.siaw.personal.rpgitems.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Effect;
import java.util.Random;
import java.util.ArrayList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;

public class Skills {
    final Lore lore;
    Location loc;
    
    public Skills() {
        lore = new Lore();
    }
    
    public void selection(ItemStack weapon, Player damager, Entity attacked) {
        if (weapon.hasItemMeta()) {
            ArrayList<String> lores = lore.getLores(weapon);
            if (lores != null) {
                ArrayList<Byte> chances = new ArrayList<>();
                ArrayList<String> skillList = new ArrayList<>();
                for (String l : lores) {
                    skillList.add(lore.decodeLore(l));
                    chances.add(lore.decodeChance(l));
                }
                for (String s : skillList) {
                    for (byte c : chances) {
                        switch (s) {
                            case "arson":
                                if (doSkill(c)) {
                                    arsonSkill(attacked);
                                    continue;
                                }
                                continue;
                            case "bleeding":
                                if (doSkill(c)) {
                                    bleedingSkill(attacked);
                                    continue;
                                }
                                continue;
                            case "hit":
                                if (doSkill(c)) {
                                    hitSkill(damager);
                                    continue;
                                }
                                continue;
                            case "input":
                                if (doSkill(c)) {
                                    inputSkill(damager, (Player)attacked);
                                    continue;
                                }
                                continue;
                            case "lightning":
                                if (doSkill(c)) {
                                    lightningSkill(attacked);
                                    continue;
                                }
                                continue;
                            case "poisoning":
                                if (doSkill(c)) {
                                    poisoningSkill((Player)attacked);
                                    continue;
                                }
                                continue;
                            case "vampirism":
                                if (doSkill(c)) {
                                    vampirismSkill(damager);
                                    continue;
                                }
                                continue;
                            case "wither":
                                if (doSkill(c)) {
                                    witherSkill((Player)attacked);
                                    continue;
                                }
                                continue;
                            case "shield":
                                if (doSkill(c)) {
                                    shieldSkill(damager, (Player)attacked);
                                }
                        }
                    }
                }
            }
        }
    }
    
    public boolean doSkill(byte chance) {
        Random rnd = new Random();
        byte randomNum = (byte)rnd.nextInt(100);
        return randomNum <= chance;
    }
    
    public void arsonSkill(Entity attacked) {
        attacked.setFireTicks(100);
    }
    
    public void bleedingSkill(Entity attacked) {
        loc = attacked.getLocation();
        attacked.getWorld().playEffect(loc, Effect.STEP_SOUND, (Object)Material.REDSTONE_BLOCK);
        Bukkit.getScheduler().runTaskLater(Main.getInst(), () -> {
            if (!attacked.isDead()) {
                loc = attacked.getLocation();
                attacked.getWorld().playEffect(loc, Effect.STEP_SOUND, (Object)Material.REDSTONE_BLOCK);
            }
        }, 80L);
    }
    
    public void hitSkill(Player damager) {
        damager.setLastDamage(damager.getLastDamage() * 0.2);
    }
    
    public void lightningSkill(Entity attacked) {
        Location loc = attacked.getLocation();
        attacked.getWorld().strikeLightning(loc);
    }
    
    public void poisoningSkill(Player attacked) {
        attacked.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));
    }
    
    public void vampirismSkill(Player damager) {
        double newHealth = damager.getLastDamage() / 2.0;
        damager.setHealth(damager.getHealth() + newHealth);
    }
    
    public void witherSkill(Player attacked) {
        attacked.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 1));
    }
    
    public void inputSkill(Player damager, Player attacked) {
        ItemStack[] armorContents = attacked.getInventory().getArmorContents();
        for (byte slot = 3; slot > -1; --slot) {
            if (!armorContents[slot].getType().isAir() && (armorContents[slot].getType() == Material.DIAMOND_BOOTS || armorContents[slot].getType() == Material.DIAMOND_LEGGINGS || armorContents[slot].getType() == Material.DIAMOND_CHESTPLATE || armorContents[slot].getType() == Material.DIAMOND_HELMET || armorContents[slot].getType() == Material.NETHERITE_BOOTS || armorContents[slot].getType() == Material.NETHERITE_LEGGINGS || armorContents[slot].getType() == Material.NETHERITE_CHESTPLATE || armorContents[slot].getType() == Material.NETHERITE_HELMET)) {
                damager.setLastDamage(0.0);
            }
        }
    }
    
    public void shieldSkill(Player damager, Player attacked) {
        if (damager.getLastDamage() > 0.0) {
            Material itemInMainHand = attacked.getInventory().getItemInMainHand().getType();
            Material itemInOffHand = attacked.getInventory().getItemInOffHand().getType();
            ItemStack air = new ItemStack(Material.AIR, 1);
            if (itemInMainHand == Material.SHIELD) {
                attacked.getInventory().setItemInMainHand(air);
            } else if (itemInOffHand == Material.SHIELD) {
                attacked.getInventory().setItemInOffHand(air);
            }
        }
    }
}
