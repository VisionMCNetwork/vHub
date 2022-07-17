package me.atyre.hub.scoreboard;

import me.atyre.hub.Hub;
import me.blazingtide.pistol.adapter.PistolAdapter;
import org.bukkit.entity.Player;

import java.util.List;

public class HubScoreboardAdapter implements PistolAdapter {
    @Override
    public String getTitle(Player player) {
        return Hub.getInstance().getScoreboardConfig().getString("scoreboard-title");
    }

    @Override
    public List<String> getLines(Player player) {
        return null;
    }
}
