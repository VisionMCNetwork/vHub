package me.atyre.hub.listeners;

import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.ItemConstants;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class Enderbutt implements Listener {
    private final Hub plugin;
    private Map<UUID, EnderPearl> lastPearl = new HashMap<>();

    // Enderbutt Logic done by 5170

    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        if (!plugin.getGeneralConfig().getBoolean("enderbutt.enabled")) return;

        if(event.getDismounted() instanceof EnderPearl && event.getEntity() instanceof Player) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    lastPearl.get(event.getEntity().getUniqueId()).remove();
                }
            }.runTaskLater(plugin, 5);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onButtJoin(PlayerJoinEvent event) {
        if (!plugin.getGeneralConfig().getBoolean("enderbutt.enabled")) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                event.getPlayer().getInventory().setItem(plugin.getGeneralConfig().getInt("enderbutt.item.slot"), ItemConstants.ENDER_BUTT);
            }
        }.runTaskLater(plugin, 10);
    }
    @EventHandler
    public void onButtQuit(PlayerQuitEvent event) {
        lastPearl.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onThrow(PlayerInteractEvent e) {
        Action action = e.getAction();
        if (!plugin.getGeneralConfig().getBoolean("enderbutt.enabled")) return;

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            ItemStack itemStack = player.getInventory().getItemInHand();
            if (itemStack.getType() == Material.AIR) return;
            if (itemStack.getType() == Material.ENDER_PEARL) {
                e.setCancelled(true);
                e.getPlayer().getInventory().setItem(plugin.getGeneralConfig().getInt("enderbutt.item.slot"), ItemConstants.ENDER_BUTT);
                if (lastPearl.get(e.getPlayer().getUniqueId()) != null) {
                    lastPearl.get(e.getPlayer().getUniqueId()).remove();
                }
                EnderPearl enderbutt = player.launchProjectile(EnderPearl.class);
                enderbutt.setVelocity(player.getLocation().getDirection().multiply(1.6F));
                enderbutt.setPassenger(player);
                lastPearl.put(player.getUniqueId(), enderbutt);
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }

}
