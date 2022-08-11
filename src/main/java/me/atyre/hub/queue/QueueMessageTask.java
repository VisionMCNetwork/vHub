package me.atyre.hub.queue;

import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class QueueMessageTask extends BukkitRunnable {
    private final Hub plugin;

    public void run() {
        if (plugin.getQueueManager().getEnabledQueues() != null) {
            for (Queue queue : plugin.getQueueManager().getEnabledQueues()) {
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
