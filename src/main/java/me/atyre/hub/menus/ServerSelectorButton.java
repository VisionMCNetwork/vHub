package me.atyre.hub.menus;

import dev.vnco.menu.button.Button;
import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.ItemBuilder;
import me.atyre.hub.utils.MessageUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ServerSelectorButton extends Button {
    private final Player player;
    private final ConfigurationSection itemSection;

    @Override
    public ItemStack getButtonItem() {
        List<String> lore = new ArrayList<>();

        for (String s : itemSection.getStringList("lore")) {
            lore.add(MessageUtil.translate(PlaceholderAPI.setPlaceholders(player, s)));
        }
        return new ItemBuilder(Material.getMaterial(itemSection.getString("type"))).name(PlaceholderAPI.setPlaceholders(player, MessageUtil.translate((itemSection.getString("name"))))).amount(itemSection.getInt("amount")).lore(lore).build();
    }

    @Override
    public int getSlot() {
        return itemSection.getInt("slot");
    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }
}
