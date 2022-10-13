package me.atyre.hub.menus;

import me.atyre.hub.Hub;
import me.atyre.hub.utils.CustomConfig;
import me.atyre.hub.utils.ItemConstants;
import me.atyre.hub.utils.MessageUtil;
import me.atyre.hub.utils.menu.Button;
import me.atyre.hub.utils.menu.Menu;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MinigamesServerMenu extends Menu {
    @Override
    public String getTitle(Player player) {
        return MessageUtil.translate(Hub.getInstance().getServerSelectorConfig().getString("server-selector.minigames-inventory.title"));
    }

    @Override
    public int getSize() {
        int rows = Hub.getInstance().getServerSelectorConfig().getInt("server-selector.minigames-inventory.rows");
        return 9 * rows;
    }

    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        CustomConfig serverSelectorConfig = Hub.getInstance().getServerSelectorConfig();
        ConfigurationSection itemSection = serverSelectorConfig.getConfigurationSection("server-selector.minigames-inventory.items");

        for (String itemKey : itemSection.getKeys(false)) {
            ConfigurationSection currentItemSection = itemSection.getConfigurationSection(itemKey);
            buttons.put(currentItemSection.getInt("slot"), new MinigamesButton(currentItemSection));
        }

        for (int i = 0; i < getSize(); i++) {
            if (buttons.get(i) == null) {
                buttons.put(i, Button.placeholder(ItemConstants.FILLABLE_ITEM.getType(), (byte) 15, ItemConstants.FILLABLE_ITEM.getItemMeta().getDisplayName()));
            }
        }

        return buttons;
    }
}
