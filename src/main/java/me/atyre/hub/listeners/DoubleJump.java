package me.atyre.hub.listeners;

import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

@RequiredArgsConstructor
public class DoubleJump implements Listener {

    private final Hub plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        if (!plugin.getGeneralConfig().getBoolean("double-jump-enabled")) return;

        p.setAllowFlight(true);
        p.setFlying(true);
    }

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent event) {
        Player p = event.getPlayer();

        if (!plugin.getGeneralConfig().getBoolean("double-jump-enabled")) return;
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (p.isFlying()) return;

        event.setCancelled(true);
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setVelocity(p.getLocation().getDirection().multiply(1.5D).setY(1.0D));
        p.playSound(p.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
        p.setAllowFlight(true);
    }
}
