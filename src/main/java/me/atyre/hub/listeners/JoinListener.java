package me.atyre.hub.listeners;

import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.ItemConstants;
import me.atyre.hub.utils.MessageUtil;
import me.atyre.hub.utils.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class JoinListener implements Listener {
    private final Hub plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration spawnConfig = plugin.getSpawnConfig().getFileConfig();

        event.setJoinMessage(null);
        player.getInventory().clear();

        if (spawnConfig.getConfigurationSection("spawn-location") != null) {
            PlayerUtil.teleportToSpawn(plugin, player);
        } else {
            player.sendMessage(ChatColor.RED + "The spawn location was invalid, please contact a server administrator.");
        }

        for (String string : plugin.getMessagesConfig().getStringList("motd")) {
            player.sendMessage(MessageUtil.translate(string));
        }

        player.getInventory().setItem(spawnConfig.getInt("server-selector-slot"), ItemConstants.SERVER_SELECTOR);
    }
}
