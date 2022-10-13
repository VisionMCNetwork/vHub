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

        try {
            String fileName = name + ".yml";
            this.file = new File(plugin.getDataFolder(), fileName);

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        refresh();
    }

    public void save() {
        try {
            if (file != null && fileConfig != null) {
                fileConfig.save(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        save();
        this.fileConfig = YamlConfiguration.loadConfiguration(file);
    }
}
