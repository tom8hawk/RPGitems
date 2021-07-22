package ru.siaw.personal.rpgitems.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import ru.siaw.personal.rpgitems.utilities.Skills;

public class Events implements Listener {
    private final Skills skills = new Skills();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Player damager = (Player) e.getDamager();
        Entity attacked = e.getEntity();
        ItemStack weapon = damager.getInventory().getItemInMainHand();
        Material weaponType = weapon.getType();
        if (weaponType == Material.IRON_AXE || weaponType == Material.IRON_SWORD || weaponType == Material.GOLDEN_AXE || weaponType == Material.GOLDEN_SWORD ||
                weaponType == Material.DIAMOND_AXE || weaponType == Material.DIAMOND_SWORD || weaponType == Material.NETHERITE_AXE || weaponType == Material.NETHERITE_SWORD) {
            skills.selection(weapon, damager, attacked);
        }
    }
}
