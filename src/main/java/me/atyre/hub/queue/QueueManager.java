package me.atyre.hub.queue;

import lombok.Getter;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Getter
public class QueueManager {
    private HashMap<String, Queue> queuesMap = new HashMap<>();
    private HashMap<String, Queue> queuesServerMap = new HashMap<>();
    private List<Queue> queues = new ArrayList<>();
    private List<Queue> enabledQueues = new ArrayList<>();

    public boolean createQueue(String name, String bungeeServer) {
        if (this.queuesMap.containsKey(name)) return false;

        Config queueCfg = Hub.getInstance().getQueueConfig();
        FileConfiguration queueConfig = queueCfg.getFileConfig();

        queueConfig.set("queues." + name + ".name", name);
        queueConfig.set("queues." + name + ".bungeeServer", bungeeServer);
        queueConfig.set("queues." + name + ".enabled", true);
        queueCfg.refresh();

        Queue queue = new Queue(name, bungeeServer);

        this.queuesMap.put(name, queue);
        this.queuesServerMap.put(bungeeServer, queue);
        this.enabledQueues.add(queue);
        queue.setEnabled(true);
        this.queues.add(queue);

        return true;
    }

    public void removeQueue(Queue queue) {
        Config queueCfg = Hub.getInstance().getQueueConfig();
        FileConfiguration queueConfig = queueCfg.getFileConfig();

        queueConfig.set("queues." + queue.getName(), null);
        queueCfg.refresh();

        this.queuesMap.remove(queue);
        this.queuesServerMap.remove(queue);
        this.enabledQueues.remove(queue);
        queue.setEnabled(false);
        this.queues.remove(queue);
    }

    public void loadQueues() {
        ConfigurationSection queueSection = Hub.getInstance().getQueueConfig().getFileConfig().getConfigurationSection("queues");
        Set<String> queueKeys = queueSection.getKeys(false);

        for (String queueKey : queueKeys) {
            ConfigurationSection currentQueueSection = queueSection.getConfigurationSection(queueKey);

            String name = currentQueueSection.getString("name");
            String bungeeServer = currentQueueSection.getString("bungeeServer");
            boolean enabled = currentQueueSection.getBoolean("enabled");

            Queue queue = new Queue(name, bungeeServer);

            this.queuesMap.put(name, queue);
            this.queuesServerMap.put(bungeeServer, queue);
            queue.setEnabled(enabled);
            this.enabledQueues.add(queue);
            this.queues.add(queue);
        }
    }

    public String getQueueName(Queue queue) {
        for (Queue q : getQueues()) {
            if (q == queue) {
                return q.getName();
            }
        }
        return null;
    }

    public Queue getQueueFromName(String name) {
        for (Queue queue : getQueues()) {
            if (queue.getName().equals(name)) {
                return queue;
            }
        }
        return null;
    }

    public Queue getQueueFromServer(String bungeeServer) {
        if (this.queuesServerMap.containsKey(bungeeServer)) {
            return this.queuesServerMap.get(bungeeServer);
        }
        return null;
    }

    public Queue getQueue(Player player) {
        for (Queue queue : this.queues) {
            if (queue.getQueuedPlayers().contains(player)) {
                return queue;
            }
        }
        return null;
    }

    public boolean isPlayerInQueue(Player player) {
        for (Queue queue : this.queues) {
            if (queue.getQueuedPlayers().contains(player))
                return true;
        }
        return false;
    }
}
