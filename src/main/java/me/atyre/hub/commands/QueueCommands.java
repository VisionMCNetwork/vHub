package me.atyre.hub.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import lombok.RequiredArgsConstructor;
import me.atyre.hub.Hub;
import me.atyre.hub.queue.Queue;
import me.atyre.hub.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@CommandAlias("queue")
public class QueueCommands extends BaseCommand {
    private final Hub plugin;
    
    @Default
    @HelpCommand
    @Syntax("")
    public void onHelp(Player sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("create")
    @CommandPermission("hub.command.queue.create")
    @Syntax("<name> <target server>")
    @Description("Creates a queue with the given name and the target bungee server.")
    public void createQueue(Player sender, String queueName, String bungeeServer) {
        if (plugin.getQueueManager().getQueuesMap().get(queueName) == null && plugin.getQueueManager().getQueuesServerMap().get(bungeeServer) == null) {
            plugin.getQueueManager().createQueue(queueName, bungeeServer);
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-create").replace("<queue_name>", queueName)));
        } else if (plugin.getQueueManager().getQueuesMap().get(queueName) != null && plugin.getQueueManager().getQueuesServerMap().get(bungeeServer) == null) {
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-name-already-exists").replace("<queue_name>", queueName)));
        } else if (plugin.getQueueManager().getQueuesMap().get(queueName) == null && plugin.getQueueManager().getQueuesServerMap().get(bungeeServer) != null) {
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-server-already-exists").replace("<queue_name>", queueName)));
        } else {
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-already-exists").replace("<queue_name>", queueName)));
        }
    }

    @Subcommand("remove|delete")
    @CommandPermission("hub.command.queue.remove")
    @Syntax("<queue>")
    @Description("Removes a queue if that queue exists.")
    public void removeQueue(Player sender, Queue queue) {
        plugin.getQueueManager().removeQueue(queue);
        sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-remove").replace("<queue_name>", queue.getName())));
    }

    @Subcommand("pause")
    @CommandPermission("hub.command.queue.pause")
    @Syntax("<queue>")
    @Description("Pauses the given queue.")
    public void pauseQueue(Player sender, Queue queue) {
        if (plugin.getQueueManager().getEnabledQueues().contains(queue)) {
            plugin.getQueueManager().getEnabledQueues().remove(queue);
            plugin.getQueueConfig().getFileConfig().set("queues." + queue.getName() + ".enabled", false);
            plugin.getQueueConfig().refresh();
            queue.setEnabled(false);
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-pause").replace("<queue_name>", queue.getName())));
        } else {
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-already-paused").replace("<queue_name>", queue.getName())));
        }
    }

    @Subcommand("unpause|resume")
    @CommandPermission("hub.command.queue.unpause")
    @Syntax("<queue>")
    @Description("Resumes the given queue.")
    public void unPauseQueue(Player sender, Queue queue) {
        if (!plugin.getQueueManager().getEnabledQueues().contains(queue)) {
            plugin.getQueueManager().getEnabledQueues().add(queue);
            plugin.getQueueConfig().getFileConfig().set("queues." + queue.getName() + ".enabled", true);
            plugin.getQueueConfig().refresh();
            queue.setEnabled(true);
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-unpause").replace("<queue_name>", queue.getName())));
        } else {
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-not-paused").replace("<queue_name>", queue.getName())));
        }
    }

    @Subcommand("join")
    @CommandAlias("joinqueue")
    @Syntax("<queue>")
    @Description("Join a queue.")
    public void joinQueue(Player sender, Queue queue) {
        if (queue.getQueuedPlayers().contains(sender)) {
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("player-already-in-queue").replace("<queue_name>", queue.getName())));
        } else {
            queue.addPlayer(sender);
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-join").replace("<queue_name>", queue.getName())));
        }
    }

    @Subcommand("leave")
    @CommandAlias("leavequeue")
    @Description("Leaves your current queue.")
    public void leaveQueue(Player sender) {
        if (plugin.getQueueManager().isPlayerInQueue(sender)) {
            Queue queue = plugin.getQueueManager().getQueue(sender);
            queue.removePlayer(sender);
            sender.sendMessage(MessageUtil.translate(plugin.getMessagesConfig().getString("queue-leave").replace("<queue_name>", queue.getName())));
        }
    }

    @Subcommand("list")
    @CommandPermission("hub.command.queue.list")
    @Description("Lists all the queues.")
    public void listQueues(Player sender) {
        List<String> queueList = new ArrayList<>();
        List<Queue> queues = plugin.getQueueManager().getQueues();
        if (queues.isEmpty()) {
            queueList.add(ChatColor.RED + "There are no queues to show.");
        } else {
            queueList.add(ChatColor.GREEN + "Enabled" + ChatColor.WHITE + ", " + ChatColor.RED + "Disabled/Paused " + ChatColor.WHITE + "- Bungee server is in parenthesis");
            for (Queue queue : queues)
                queueList.add((queue.isEnabled() ? ChatColor.GREEN : ChatColor.RED) + queue.getName() + " (" + queue.getBungeeServer() + ")");
            for (String string : queueList)
                sender.sendMessage(string);
        }
    }
}
