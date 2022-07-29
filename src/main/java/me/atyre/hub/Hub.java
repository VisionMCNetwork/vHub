package me.atyre.hub;

import lombok.Getter;
import me.atyre.hub.listeners.DoubleJump;
import me.atyre.hub.listeners.Enderbutt;
import me.atyre.hub.listeners.JoinListener;
import me.atyre.hub.queue.QueueManager;
import me.atyre.hub.scoreboard.HubScoreboardAdapter;
import me.atyre.hub.utils.Config;
import me.atyre.hub.utils.CustomConfig;
import me.blazingtide.pistol.Pistol;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Hub extends JavaPlugin {
    @Getter private static Hub instance;

    private QueueManager queueManager;

    private Config spawnConfig, queueConfig;
    private CustomConfig generalConfig, messagesConfig, scoreboardConfig, serverSelectorConfig;

    private Pistol pistol;

    private List<Player> buildPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        loadAllConfigs();

        this.queueManager = new QueueManager();

        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("doMobSpawning", "false");
            world.setGameRuleValue("doDaylightCycle", "false");
        }

        pistol = new Pistol(this, new HubScoreboardAdapter());

        getServer().getPluginManager().registerEvents(new Enderbutt(this), this);
        getServer().getPluginManager().registerEvents(new DoubleJump(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        System.out.println("[Hub] Hub plugin is now enabled!");
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void loadAllConfigs() {
        this.generalConfig = new CustomConfig(this, "config");
        this.messagesConfig = new CustomConfig(this, "messages");
        this.scoreboardConfig = new CustomConfig(this, "scoreboard");
        this.serverSelectorConfig = new CustomConfig(this, "server-selector");

        this.spawnConfig = new Config(this, "spawn");
        this.queueConfig = new Config(this, "queue");
    }
}
