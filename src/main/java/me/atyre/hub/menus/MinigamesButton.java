package me.atyre.hub.menus;

import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.ItemBuilder;
import me.atyre.hub.utils.MessageUtil;
import me.atyre.hub.utils.menu.Button;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MinigamesButton extends Button {
    private final ConfigurationSection itemSection;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<>();

        for (String s : itemSection.getStringList("lore")) {
            lore.add(MessageUtil.translate(PlaceholderAPI.setPlaceholders(player, s.replace("<queue_max_position>", String.valueOf(Hub.getInstance().getQueueManager().getQueueFromName(Hub.getInstance().getServerSelectorConfig().getString("server-name")).getQueuedPlayers().size())))));
        }
        return new ItemBuilder(Material.getMaterial(itemSection.getString("type"))).name(PlaceholderAPI.setPlaceholders(player, MessageUtil.translate((itemSection.getString("name"))))).amount(itemSection.getInt("amount")).lore(lore).build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        player.performCommand(itemSection.getString("command"));
        player.closeInventory();
    }
}
