package me.atyre.hub.utils;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@Getter
public class Config {
    private final JavaPlugin plugin;
    private File file;
    private FileConfiguration fileConfig;

    public Config(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        String fileName = name + ".yml";
        this.file = new File(plugin.getDataFolder(), fileName);

        refreshConfig();
    }

    public void refreshConfig() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.fileConfig = YamlConfiguration.loadConfiguration(file);
    }
}
