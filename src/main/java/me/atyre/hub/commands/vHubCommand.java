package me.atyre.hub.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("vhub")
public class vHubCommand extends BaseCommand {
    @Default
    @Syntax("")
    public void vHub(Player sender) {
        String[] msg = {
                MessageUtil.translate("&5&lvHub &7- &f" + Hub.getInstance().getDescription().getVersion()),
                MessageUtil.translate(" "),
                MessageUtil.translate("&eAuthor: &f" + Hub.getInstance().getDescription().getAuthors())
        };

        sender.sendMessage(msg);
    }

    @Subcommand("reload")
    @CommandPermission("hub.command.reload")
    public void reload(Player sender) {
        Hub.getInstance().loadAllConfigs();
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_PURPLE + "vHub" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "The configs have been saved.");
    }
}
