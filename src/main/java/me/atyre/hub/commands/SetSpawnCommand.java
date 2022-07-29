package me.atyre.hub.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import me.atyre.hub.Hub;
import me.atyre.hub.utils.Config;
import me.atyre.hub.utils.MessageUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("setspawn|setlobby")
@CommandPermission("hub.command.setspawn")
public class SetSpawnCommand extends BaseCommand {
    @Default
    @Syntax("")
    public void setSpawn(Player sender) {
        Config spawnCfg = Hub.getInstance().getSpawnConfig();
        FileConfiguration spawnConfig = spawnCfg.getFileConfig();
        spawnConfig.set("spawn-location.x", sender.getLocation().getX());
        spawnConfig.set("spawn-location.y", sender.getLocation().getY());
        spawnConfig.set("spawn-location.z", sender.getLocation().getZ());
        spawnConfig.set("spawn-location.yaw", (double) sender.getLocation().getYaw());
        spawnConfig.set("spawn-location.pitch", (double) sender.getLocation().getPitch());
        spawnCfg.refresh();
        sender.sendMessage(MessageUtil.translate(Hub.getInstance().getMessagesConfig().getString("spawn-set")));
    }
}
