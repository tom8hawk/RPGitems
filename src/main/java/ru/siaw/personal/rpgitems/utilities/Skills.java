package ru.siaw.personal.rpgitems.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.siaw.personal.rpgitems.Main;

import java.util.ArrayList;
import java.util.Random;

public class Skills {
    private final Lore lore = new Lore();
    private Location loc;

    public void selection(ItemStack weapon, Player damager, Entity attacked) {
        if (weapon.hasItemMeta() && weapon.getItemMeta().hasLore()) {
            ArrayList<String> lores = new ArrayList<>(lore.getLores(weapon));
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
                            }
                            break;
                        case "bleeding":
                            if (doSkill(c)) {
                                bleedingSkill(attacked);
                            }
                            break;
                        case "hit":
                            if (doSkill(c)) {
                                hitSkill(damager);
                            }
                            break;
                        case "input":
                            if (doSkill(c)) {
                                inputSkill(damager, (Player) attacked);
                            }
                            break;
                        case "lightning":
                            if (doSkill(c)) {
                                lightningSkill(attacked);
                            }
                            break;
                        case "poisoning":
                            if (doSkill(c)) {
                                poisoningSkill((Player) attacked);
                            }
                            break;
                        case "vampirism":
                            if (doSkill(c)) {
                                vampirismSkill(damager);
                            }
                            break;
                        case "wither":
                            if (doSkill(c)) {
                                witherSkill((Player) attacked);
                            }
                            break;
                        case "shield":
                            if (doSkill(c)) {
                                shieldSkill(damager, (Player) attacked);
                            }
                    }
                }
            }
        }
    }

    private boolean doSkill(byte chance) {
        Random rnd = new Random();
        byte randomNum = (byte) rnd.nextInt(100);
        return randomNum <= chance;
    }

    private void arsonSkill(Entity attacked) {
        attacked.setFireTicks(100);
    }

    private void bleedingSkill(Entity attacked) {
        loc = attacked.getLocation();
        attacked.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        Bukkit.getScheduler().runTaskLater(Main.getInst(), () -> {
            if (!attacked.isDead()) {
                loc = attacked.getLocation();
                attacked.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
            }
        }, 80L);
    }

    private void hitSkill(Player damager) {
        damager.setLastDamage(damager.getLastDamage() * 0.20);
    }

    private void lightningSkill(Entity attacked) {
        Location loc = attacked.getLocation();
        attacked.getWorld().strikeLightning(loc);
    }

    private void poisoningSkill(Player attacked) {
        attacked.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));
    }

    private void vampirismSkill(Player damager) {
        double newHealth = damager.getLastDamage() / 2;
        damager.setHealth(damager.getHealth() + newHealth);
    }

    private void witherSkill(Player attacked) {
        attacked.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 1));
    }

    private void inputSkill(Player damager, Player attacked) {
        ItemStack[] armorContents = attacked.getInventory().getArmorContents();
        for (byte slot = 3; slot > -1; slot--) {
            if (!armorContents[slot].getType().isAir()) {
                if (armorContents[slot].getType() == Material.DIAMOND_BOOTS || armorContents[slot].getType() == Material.DIAMOND_LEGGINGS ||
                        armorContents[slot].getType() == Material.DIAMOND_CHESTPLATE || armorContents[slot].getType() == Material.DIAMOND_HELMET ||
                        armorContents[slot].getType() == Material.NETHERITE_BOOTS || armorContents[slot].getType() == Material.NETHERITE_LEGGINGS ||
                        armorContents[slot].getType() == Material.NETHERITE_CHESTPLATE || armorContents[slot].getType() == Material.NETHERITE_HELMET) {
                    damager.setLastDamage(0);
                }
            }
        }
    }

    private void shieldSkill(Player damager, Player attacked) {
        if (damager.getLastDamage() > 0) {
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
