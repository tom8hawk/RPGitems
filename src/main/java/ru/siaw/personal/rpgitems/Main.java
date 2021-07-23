package ru.siaw.personal.rpgitems;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import ru.siaw.personal.rpgitems.listeners.Events;
import ru.siaw.personal.rpgitems.listeners.TabComplete;
import ru.siaw.personal.rpgitems.listeners.Commands;
import java.util.Objects;
import ru.siaw.personal.rpgitems.utilities.FileManager;
import ru.siaw.personal.rpgitems.utilities.Lore;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main inst;
    final Lore lore;
    
    public Main() {
        lore = new Lore();
        inst = this;
    }
    
    public static Main getInst() {
        return inst;
    }
    
    public void onEnable() {
        FileManager.checkFiles();
        Objects.requireNonNull(getCommand("rpg")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("rpg")).setTabCompleter(new TabComplete());
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(this, PacketType.Play.Server.SET_SLOT) {
            public void onPacketSending(PacketEvent e) {
                PacketContainer packet = e.getPacket();
                ItemStack stack = packet.getItemModifier().read(0);
                if (!stack.getType().isAir()) {
                    lore.removePacketLore(stack, packet);
                }
            }
        });
        protocolManager.addPacketListener(new PacketAdapter(this, PacketType.Play.Server.WINDOW_ITEMS) {
            public void onPacketSending(PacketEvent e) {
                PacketContainer packet = e.getPacket();
                ItemStack[] elements = packet.getItemArrayModifier().read(0);
                for (byte slot = (byte)elements.length; slot >= 0; --slot) {
                    if (!elements[slot].getType().isAir()) {
                        lore.removePacketLore(elements[slot], packet);
                    }
                }
            }
        });
    }
    
    public void onDisable() {
    }
}
