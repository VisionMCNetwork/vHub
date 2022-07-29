package me.atyre.hub.utils;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@Getter
public class CustomConfig extends YamlConfiguration {
    private final JavaPlugin plugin;
    private File file;

    public CustomConfig(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        String fileName = name + ".yml";
        this.file = new File(plugin.getDataFolder(), fileName);

        try {
            refreshConfig();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void refreshConfig() throws IOException, InvalidConfigurationException {
        if (!file.exists()) {
            plugin.saveResource(file.getName(), false);
        }

        save(file);

        load(file);
    }
}
