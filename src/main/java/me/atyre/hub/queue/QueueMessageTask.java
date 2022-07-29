package me.atyre.hub.queue;

import me.atyre.hub.Hub;
import me.atyre.hub.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class QueueMessageTask extends BukkitRunnable {
    public void run() {
        if (Hub.getInstance().getQueueManager().getEnabledQueues() != null) {
            List<Queue> queues = Hub.getInstance().getQueueManager().getEnabledQueues();
            for (Queue queue : queues) {
                if (!queue.getQueuedPlayers().isEmpty())
                    for (Player player : queue.getQueuedPlayers()) {
                        for (String string : Hub.getInstance().getMessagesConfig().getStringList("queue-message")) {
                            player.sendMessage(MessageUtil.translate(string).replace("<queue_name>", queue.getName()).replace("<queue_position>", String.valueOf(queue.getQueuedPlayers().indexOf(player) + 1)).replace("<queue_max_position>", String.valueOf(queue.getQueuedPlayers().size())));
                        }
                    }
            }
        }
    }
}
