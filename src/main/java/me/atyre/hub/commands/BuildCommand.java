package me.atyre.hub.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.MessageUtil;
import me.atyre.hub.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("build|buildmode")
@CommandPermission("hub.command.build")
public class BuildCommand extends BaseCommand {
    @Default
    @Syntax("[player]")
    public void build(Player sender, @Optional String targetPlayer) {
        if (targetPlayer != null) {
            OnlinePlayer target = (OnlinePlayer) Bukkit.getPlayerExact(targetPlayer);
            if (PlayerUtil.canBuild(target.getPlayer())) {
                Hub.getInstance().getBuildPlayers().remove(target.getPlayer());
                sender.sendMessage(MessageUtil.translate(Hub.getInstance().getMessagesConfig().getString("no-longer-build-mode-other")));
                target.getPlayer().sendMessage(MessageUtil.translate(Hub.getInstance().getMessagesConfig().getString("no-longer-build-mode")));
            } else {
                Hub.getInstance().getBuildPlayers().add(target.getPlayer());
                sender.sendMessage(MessageUtil.translate(Hub.getInstance().getMessagesConfig().getString("build-mode-other")));
                target.getPlayer().sendMessage(MessageUtil.translate(Hub.getInstance().getMessagesConfig().getString("build-mode")));
            }
        } else if (PlayerUtil.canBuild(sender)) {
            Hub.getInstance().getBuildPlayers().remove(sender);
            sender.sendMessage(MessageUtil.translate(Hub.getInstance().getMessagesConfig().getString("no-longer-build-mode")));
        } else {
            Hub.getInstance().getBuildPlayers().add(sender);
            sender.sendMessage(MessageUtil.translate(Hub.getInstance().getMessagesConfig().getString("build-mode")));
        }
    }
}