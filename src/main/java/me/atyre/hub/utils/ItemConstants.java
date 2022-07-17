package me.atyre.hub.utils;

import me.atyre.hub.Hub;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemConstants {
    private static final CustomConfig serverSelectorConfig = Hub.getInstance().getServerSelectorConfig();
    private static final CustomConfig config = Hub.getInstance().getGeneralConfig();

    public static final ItemStack SERVER_SELECTOR = new ItemBuilder(Material.getMaterial(serverSelectorConfig.getString("server-selector.item.type"))).name(serverSelectorConfig.getString("server-selector.item.name")).build();
    public static final ItemStack ENDER_BUTT = new ItemBuilder(Material.getMaterial(config.getString("enderbutt.item.type")), config.getInt("enderbutt.item.amount")).name(MessageUtil.translate(config.getString("enderbutt.item.name"))).build();
}
