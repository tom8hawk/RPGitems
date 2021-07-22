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

public class Skills {
    private Location loc;

    public void arsonSkill(Entity attacked) {
        attacked.setFireTicks(100);
    }

    public void bleedingSkill(Entity attacked) {
        loc = attacked.getLocation();
        attacked.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        Bukkit.getScheduler().runTaskLater(Main.getInst(), () -> {
            if (!attacked.isDead()) {
                loc = attacked.getLocation();
                attacked.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
            }
        }, 80L);
    }

    public void hitSkill(Player damager) {
        damager.setLastDamage(damager.getLastDamage() * 0.20);
    }

    public void lightningSkill(Entity attacked) {
        Location loc = attacked.getLocation();
        attacked.getWorld().strikeLightning(loc);
    }

    public void poisoningSkill(Player attacked) {
        attacked.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));
    }

    public void vampirismSkill(Player damager) {
        double newHealth = damager.getLastDamage() / 2;
        damager.setHealth(damager.getHealth() + newHealth);
    }

    public void witherSkill(Player attacked) {
        attacked.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 1));
    }

    public void inputSkill(Player damager, Player attacked) {
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
}
