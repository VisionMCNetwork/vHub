package me.atyre.hub.queue;

import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.MessageUtil;
import me.atyre.hub.utils.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class QueueTask extends BukkitRunnable {
    private final Hub plugin;

    @Override
    public void run() {
        if (plugin.getQueueManager().getEnabledQueues() != null) {
            for (Queue queue : plugin.getQueueManager().getQueues()) {
                if (queue.isEnabled() && !queue.getQueuedPlayers().isEmpty()) {
                    Player player = queue.getQueuedPlayers().iterator().next();
                    player.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-send")).replace("<queue_server>", queue.getBungeeServer()));
                    PlayerUtil.connectToServer(player, queue.getBungeeServer());
                    queue.removePlayer(player);
                }
            }
        }
    }
}
