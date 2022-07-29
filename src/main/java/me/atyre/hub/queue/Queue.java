package me.atyre.hub.queue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Queue {
    private final String name;
    private final String bungeeServer;
    private List<Player> queuedPlayers;
    @Setter private boolean enabled;

    public void addPlayer(Player player) {
        if (this.queuedPlayers.contains(player)) {
            player.sendMessage(MessageUtil.translate(Hub.getInstance().getMessagesConfig().getString("player-already-in-queue").replace("<queue_name>", this.name)));
        } else {
            this.queuedPlayers.add(player);
            player.sendMessage(MessageUtil.translate(Hub.getInstance().getMessagesConfig().getString("queue-join")).replace("<queue_name>", this.name));
        }
    }

    public void removePlayer(Player player) {
        if (this.queuedPlayers.contains(player)) {
            this.queuedPlayers.remove(player);
            player.sendMessage(MessageUtil.translate(Hub.getInstance().getMessagesConfig().getString("queue-leave")).replace("<queue_name>", this.name));
        }
    }
}
