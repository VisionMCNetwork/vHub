package me.atyre.hub.utils;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.atyre.hub.Hub;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerUtil {
    public static void connectToServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("ConnectOther");
        out.writeUTF(player.getName());
        out.writeUTF(server);

        Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        assert p != null;

        p.sendPluginMessage(Hub.getInstance(), "BungeeCord", out.toByteArray());
    }

    public static void teleportToSpawn(Hub plugin, Player player) {
        FileConfiguration spawnConfig = plugin.getSpawnConfig().getFileConfig();
        World world = Bukkit.getWorld(spawnConfig.getString("spawn-location.world"));

        player.teleport(new Location(world,
                spawnConfig.getDouble("spawn-location.x"),
                spawnConfig.getDouble("spawn-location.y"),
                spawnConfig.getDouble("spawn-location.z"),
                (float) spawnConfig.get("spawn-location.yaw"),
                (float) spawnConfig.getDouble("spawn-location.pitch")));
    }

    public static boolean canBuild(Player player) {
        return Hub.getInstance().getBuildPlayers().contains(player);
    }

}
