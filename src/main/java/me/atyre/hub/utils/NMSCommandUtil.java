package me.atyre.hub.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;

public class NMSCommandUtil {
    // https://www.spigotmc.org/threads/small-easy-register-command-without-plugin-yml.38036/

    public static void registerCommand(String commandName, BukkitCommand command) throws NoSuchFieldException, IllegalAccessException {
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

        bukkitCommandMap.setAccessible(true);

        CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

        commandMap.register(commandName, command);
    }
}
