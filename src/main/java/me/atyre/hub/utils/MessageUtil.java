package me.atyre.hub.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {
    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> translate(List<String> text) {
        List<String> strings = new ArrayList<>();
        for (String string : text) {
            strings.add(translate(string));
        }

        return strings;
    }
}
