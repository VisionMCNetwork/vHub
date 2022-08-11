package me.atyre.hub.listeners;

import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class QuitListener implements Listener {
    private final Hub plugin;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);

        if (PlayerUtil.canBuild(player)) {
            plugin.getBuildPlayers().remove(player);
        }

        if (plugin.getQueueManager().isPlayerInQueue(player)) {
            plugin.getQueueManager().getQueue(player).removePlayer(player);
        }
    }
}
