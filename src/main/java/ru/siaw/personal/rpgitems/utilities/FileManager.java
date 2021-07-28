package ru.siaw.personal.rpgitems.utilities;

import org.bukkit.configuration.file.YamlConfiguration;
import ru.siaw.personal.rpgitems.Main;

import java.io.File;

public class FileManager {
    private static YamlConfiguration msg;

    public static void checkFiles() {
        if (!Main.getInst().getDataFolder().exists())
            Main.getInst().getDataFolder().mkdir();
        File message = new File(Main.getInst().getDataFolder(), "message.yml");
        if (!message.exists())
            Main.getInst().saveResource("message.yml", true);
        msg = YamlConfiguration.loadConfiguration(message);
    }

    public static YamlConfiguration getMsg() {
        return msg;
    }
}