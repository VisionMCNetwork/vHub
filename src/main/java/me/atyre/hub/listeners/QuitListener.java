package me.atyre.hub.listeners;

import me.atyre.hub.Hub;
import me.atyre.hub.utils.PlayerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        if (PlayerUtil.canBuild(event.getPlayer())) {
            Hub.getInstance().getBuildPlayers().remove(event.getPlayer());
        }
    }
}
