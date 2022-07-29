package me.atyre.hub.listeners;

import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.menus.ServerSelectorMenu;
import me.atyre.hub.utils.ItemConstants;
import me.atyre.hub.utils.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private final Hub plugin;

    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent event) {
        event.setCancelled(true);
        event.setFoodLevel(20);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (!PlayerUtil.canBuild(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (!PlayerUtil.canBuild(event.getPlayer())) {
            event.getItem().remove();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item.getType() != null && item.getType() == ItemConstants.SERVER_SELECTOR.getType() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            new ServerSelectorMenu().openMenu(event.getPlayer());
        }
    }

    @EventHandler
    public void onMoveItem(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (!PlayerUtil.canBuild(player) && (event.getInventory().getType() == InventoryType.PLAYER || event.getInventory().getType() == InventoryType.CRAFTING)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDragItem(InventoryDragEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (!PlayerUtil.canBuild(player) && (event.getInventory().getType() == InventoryType.PLAYER || event.getInventory().getType() == InventoryType.CRAFTING)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getLocation().getY() < Hub.getInstance().getConfig().getInt("void-level")) {
            PlayerUtil.teleportToSpawn(plugin, player);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!PlayerUtil.canBuild(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!PlayerUtil.canBuild(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
