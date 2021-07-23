package ru.siaw.personal.rpgitems.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Entity;
import ru.siaw.personal.rpgitems.utilities.Skills;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Listener;

public class Events implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Player damager = (Player)e.getDamager();
        Entity attacked = e.getEntity();
        ItemStack weapon = damager.getInventory().getItemInMainHand();
        Material weaponType = weapon.getType();
        if (weaponType == Material.IRON_AXE || weaponType == Material.IRON_SWORD || weaponType == Material.GOLDEN_AXE || weaponType == Material.GOLDEN_SWORD || weaponType == Material.DIAMOND_AXE || weaponType == Material.DIAMOND_SWORD || weaponType == Material.NETHERITE_AXE || weaponType == Material.NETHERITE_SWORD) {
            Skills skills = new Skills();
            skills.selection(weapon, damager, attacked);
        }
    }
}
