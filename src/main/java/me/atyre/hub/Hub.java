package me.atyre.hub;

import lombok.Getter;
import me.atyre.hub.scoreboard.HubScoreboardAdapter;
import me.atyre.hub.utils.Config;
import me.atyre.hub.utils.CustomConfig;
import me.blazingtide.pistol.Pistol;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Hub extends JavaPlugin {
    @Getter private static Hub instance;

    private Config spawnConfig, queueConfig;
    private CustomConfig generalConfig, messagesConfig, scoreboardConfig, serverSelectorConfig;

    private Pistol pistol;

    @Override
    public void onEnable() {
        instance = this;

        this.generalConfig = new CustomConfig(this, "config");
        this.messagesConfig = new CustomConfig(this, "messages");
        this.scoreboardConfig = new CustomConfig(this, "scoreboard");
        this.serverSelectorConfig = new CustomConfig(this, "server-selector");

        this.spawnConfig = new Config(this, "spawn");
        this.queueConfig = new Config(this, "queue");

        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("doMobSpawning", "false");
            world.setGameRuleValue("doDaylightCycle", "false");
        }

        pistol = new Pistol(this, new HubScoreboardAdapter());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");


    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
