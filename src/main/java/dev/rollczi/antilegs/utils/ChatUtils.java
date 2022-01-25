/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ChatUtils {

    public static String color(String content) {
        return ChatColor.translateAlternateColorCodes('&', content);
    }

    public static List<String> color(List<String> list) {
        return list.stream().map(ChatUtils::color).collect(Collectors.toList());
    }

}
