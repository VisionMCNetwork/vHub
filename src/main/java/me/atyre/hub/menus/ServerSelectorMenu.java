package me.atyre.hub.menus;

import dev.vnco.menu.Menu;
import dev.vnco.menu.button.Button;
import dev.vnco.menu.type.FillType;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.ItemConstants;
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
        return null;
    }
}
