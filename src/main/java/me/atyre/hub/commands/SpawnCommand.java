package me.atyre.hub.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import me.atyre.hub.menus.MinigamesServerMenu;
import org.bukkit.entity.Player;

@CommandAlias("spawn")
public class SpawnCommand extends BaseCommand {
    @Default
    @Syntax("")
    public void minigamesMenu(Player sender) {
        new MinigamesServerMenu().openMenu(sender);
    }
}
