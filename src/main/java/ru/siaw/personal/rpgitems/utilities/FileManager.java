package ru.siaw.personal.rpgitems.utilities;

import java.io.File;
import ru.siaw.personal.rpgitems.Main;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
    static YamlConfiguration msg;
    
    public static void checkFiles() {
        if (!Main.getInst().getDataFolder().exists()) {
            Main.getInst().getDataFolder().mkdir();
        }
        File message = new File(Main.getInst().getDataFolder(), "message.yml");
        if (!message.exists()) {
            Main.getInst().saveResource("message.yml", true);
        }
        FileManager.msg = YamlConfiguration.loadConfiguration(message);
    }
    
    public static YamlConfiguration getMsg() {
        return FileManager.msg;
    }
}
