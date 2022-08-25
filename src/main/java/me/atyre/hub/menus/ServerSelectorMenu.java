package me.atyre.hub.menus;

import dev.vnco.menu.Menu;
import dev.vnco.menu.button.Button;
import dev.vnco.menu.type.FillType;
import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.CustomConfig;
import me.atyre.hub.utils.ItemConstants;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ServerSelectorMenu extends Menu {

    public ServerSelectorMenu() {
        super(Hub.getInstance().getServerSelectorConfig().getString("server-selector.inventory.title"), 9 * Hub.getInstance().getServerSelectorConfig().getInt("server-selector.inventory.rows"));
        this.setFillEnabled(true);
        this.setFillType(FillType.ALL_EMPTY_SLOTS);
        this.setFillItemStack(ItemConstants.FILLABLE_ITEM);
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        CustomConfig serverSelectorConfig = Hub.getInstance().getServerSelectorConfig();
        ConfigurationSection itemSection = serverSelectorConfig.getConfigurationSection("server-selector.items");

        for (String itemKey : itemSection.getKeys(false)) {
            ConfigurationSection currentItemSection = itemSection.getConfigurationSection(itemKey);
            buttons.add(new ServerSelectorButton(player, currentItemSection));
        }

        return buttons;
    }
}
