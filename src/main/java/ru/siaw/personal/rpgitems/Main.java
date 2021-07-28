package ru.siaw.personal.rpgitems;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.siaw.personal.rpgitems.listeners.Commands;
import ru.siaw.personal.rpgitems.listeners.Events;
import ru.siaw.personal.rpgitems.listeners.TabComplete;
import ru.siaw.personal.rpgitems.utilities.FileManager;
import ru.siaw.personal.rpgitems.utilities.Lore;

public class Main extends JavaPlugin {
    public static Main inst;
    final Lore lore = new Lore();

    public Main() {
        inst = this;
    }

    public static Main getInst() {
        return inst;
    }

    public void onEnable() {
        FileManager.checkFiles();
        getCommand("rpg").setExecutor(new Commands());
        getCommand("rpg").setTabCompleter(new TabComplete());
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
                byte slot = (byte) elements.length;
                while (slot >= 0) {
                    if (!elements[slot].getType().isAir()) {
                        lore.removePacketLore(elements[slot], packet);
                    }
                    slot--;
                }
            }
        });
    }

    public void onDisable() {
    }
}
