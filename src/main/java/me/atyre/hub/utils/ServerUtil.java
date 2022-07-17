package me.atyre.hub.utils;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.atyre.hub.Hub;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerUtil {
    public static void connectToServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("ConnectOther");
        out.writeUTF(player.getName());
        out.writeUTF(server);

        Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        assert p != null;

        p.sendPluginMessage(Hub.getInstance(), "BungeeCord", out.toByteArray());
    }
}
