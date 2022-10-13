package me.atyre.hub.adapters;

import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.queue.Queue;
import me.atyre.hub.utils.MessageUtil;
import me.blazingtide.pistol.adapter.PistolAdapter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HubScoreboardAdapter implements PistolAdapter {
    private final Hub plugin;

    @Override
    public String getTitle(Player player) {
        return plugin.getScoreboardConfig().getString("scoreboard-title");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> scoreboardLines = new ArrayList<>();
        if (plugin.getQueueManager().isPlayerInQueue(player)) {
            Queue queue = Hub.getInstance().getQueueManager().getQueue(player);

            for (String string : plugin.getScoreboardConfig().getStringList("queue-scoreboard")) {
                scoreboardLines.add(MessageUtil.translate(PlaceholderAPI.setPlaceholders(player,
                        string.replace("<queue_name>", queue.getName())
                                .replace("<queue_position>", String.valueOf(queue.getQueuedPlayers().indexOf(player) + 1))
                                .replace("<queue_max_position>", String.valueOf(queue.getQueuedPlayers().size())))));
            }
        } else {
            for (String string : plugin.getScoreboardConfig().getStringList("scoreboard")) {
                scoreboardLines.add(MessageUtil.translate(PlaceholderAPI.setPlaceholders(player, string)));
            }
        }
        return scoreboardLines;
    }
}
