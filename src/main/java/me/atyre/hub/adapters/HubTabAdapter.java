package me.atyre.hub.adapters;

import club.frozed.tablist.adapter.TabAdapter;
import club.frozed.tablist.entry.TabEntry;
import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HubTabAdapter implements TabAdapter {
    private final Hub plugin;

    @Override
    public String getHeader(Player player) {
        return plugin.getTablistConfig().getString("tablist.header");
    }

    @Override
    public String getFooter(Player player) {
        return plugin.getTablistConfig().getString("tablist.footer");
    }

    @Override
    public List<TabEntry> getLines(Player player) {
        List<TabEntry> tabEntries = new ArrayList<>();

        List<String> row1List = Hub.getInstance().getTablistConfig().getStringList("tablist.row-1");
        List<String> row2List = Hub.getInstance().getTablistConfig().getStringList("tablist.row-2");
        List<String> row3List = Hub.getInstance().getTablistConfig().getStringList("tablist.row-3");
        List<String> row4List = Hub.getInstance().getTablistConfig().getStringList("tablist.row-4");

        for (String row1 : row1List) {
            tabEntries.add(new TabEntry(1, row1))
        }
        tabEntries.add(new TabEntry())
        return null;
    }
}
