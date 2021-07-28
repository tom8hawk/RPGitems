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
    final Lore lore = new Lore();
    Player damagerPlayer;

    public void selection(ItemStack weapon, Player damager, Entity attacked) {
        ArrayList<String> skillList = new ArrayList<>(decodeSkills(weapon));
        ArrayList<Byte> chancesList = new ArrayList<>(decodeChances(weapon));
        damagerPlayer = damager;
        Player playerAttacked = null;

        if (attacked instanceof Player) {
            playerAttacked = (Player) attacked;
        }

        if (skillList.contains("poisoning")) {
            if (doSkill(chancesList.get(skillList.indexOf("poisoning")))) {
                if (playerAttacked != null) {
                    poisoning(playerAttacked);
                }
            }
        }
        if (skillList.contains("wither")) {
            if (doSkill(chancesList.get(skillList.indexOf("wither")))) {
                if (playerAttacked != null) {
                    wither(playerAttacked);
                }
            }
        }
        if (skillList.contains("bleeding")) {
            if (doSkill(chancesList.get(skillList.indexOf("bleeding")))) {
                bleeding(attacked);
            }
        }
        if (skillList.contains("shield")) {
            if (doSkill(chancesList.get(skillList.indexOf("shield")))) {
                if (playerAttacked != null) {
                    shield(playerAttacked);
                }
            }
        }
        if (skillList.contains("vampirism")) {
            if (doSkill(chancesList.get(skillList.indexOf("vampirism")))) {
                if (playerAttacked != null) {
                    vampirism(playerAttacked);
                }
            }
        }
        if (skillList.contains("lightning")) {
            if (doSkill(chancesList.get(skillList.indexOf("lightning")))) {
                lightning(attacked);
            }
        }
        if (skillList.contains("arson")) {
            if (doSkill(chancesList.get(skillList.indexOf("arson")))) {
                arson(attacked);
            }
        }
    }

    public double selectionForDamage(Entity attacked) {
        double returnDamage = -1;

        if (attacked instanceof Player) {
            Player playerAttacked = (Player) attacked;
            ItemStack weapon = damagerPlayer.getInventory().getItemInMainHand();
            ArrayList<String> skillList = new ArrayList<>(decodeSkills(weapon));
            ArrayList<Byte> chancesList = new ArrayList<>(decodeChances(weapon));

            if (skillList.contains("hit")) {
                if (doSkill(chancesList.get(skillList.indexOf("hit")))) {
                    returnDamage = hit(playerAttacked);
                }
            }
            if (skillList.contains("input")) {
                if (doSkill(chancesList.get(skillList.indexOf("input")))) {
                    returnDamage = input(playerAttacked);
                }
            }
        }
        return returnDamage;
    }

    private boolean doSkill(byte chance) {
        Random rnd = new Random();
        byte randomNum = (byte) rnd.nextInt(100);
        return randomNum <= chance;
    }

    private ArrayList<String> decodeSkills(ItemStack weapon) {
        ArrayList<String> weaponSkills = new ArrayList<>(lore.getLores(weapon));
        ArrayList<String> skillList = new ArrayList<>();
        for (String ws : weaponSkills) {
            skillList.add(lore.decodeLore(ws));
        }
        return skillList;
    }

    private ArrayList<Byte> decodeChances(ItemStack weapon) {
        ArrayList<String> weaponChances = new ArrayList<>(lore.getLores(weapon));
        ArrayList<Byte> chancesList = new ArrayList<>();
        for (String wc : weaponChances) {
            chancesList.add(lore.decodeChance(wc));
        }
        return chancesList;
    }

    private void arson(Entity attacked) {
        attacked.setFireTicks(100);
    }

    private void bleeding(Entity attacked) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInst(), () -> {
            playBlood(attacked);
            if (attacked instanceof Player) {
                Player playerAttacked = (Player) attacked;
                for (byte col = 0; col < 3; col++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    playerAttacked.setHealth(playerAttacked.getHealth() - 1.0);;
                    playBlood(attacked);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                playBlood(attacked);
            }
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playBlood(attacked);
        });
    }

    private void playBlood(Entity attacked) {
        if (!attacked.isDead()) {
            attacked.getWorld().playEffect(attacked.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        }
    }

    private double hit(Player attacked) {
        double oldDamage = attacked.getLastDamage();
        return oldDamage + (oldDamage * 20.0 / 100);
    }

    private void lightning(Entity attacked) {
        Location loc = attacked.getLocation();
        attacked.getWorld().strikeLightning(loc);
    }

    private void poisoning(Player attacked) {
        attacked.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));
    }

    private void vampirism(Player attacked) {
        String newHealth = String.format("%.2f", attacked.getLastDamage() / 2.0);
        attacked.setHealth(Double.parseDouble(attacked.getHealth() + newHealth));
    }

    private void wither(Player attacked) {
        attacked.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 1));
    }

    private double input(Player attacked) {
        ItemStack[] armorContents = attacked.getInventory().getArmorContents();
        double returnDamage = -1;
        if ((armorContents[0] != null && armorContents[1] != null && armorContents[2] != null && armorContents[3] != null) && (armorContents[0].getType() == Material.DIAMOND_BOOTS || armorContents[0].getType() == Material.NETHERITE_BOOTS) &&
                (armorContents[1].getType() == Material.DIAMOND_LEGGINGS || armorContents[1].getType() == Material.NETHERITE_LEGGINGS) && (armorContents[2].getType() == Material.DIAMOND_CHESTPLATE || armorContents[2].getType() == Material.NETHERITE_CHESTPLATE) &&
                (armorContents[3].getType() == Material.DIAMOND_HELMET || armorContents[3].getType() == Material.NETHERITE_HELMET)) {
            returnDamage = 0.0;
        }
        return returnDamage;
    }

    private void shield(Player attacked) {
        if (attacked.getLastDamage() > 0.0) {
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
