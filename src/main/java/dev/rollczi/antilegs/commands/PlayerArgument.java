/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.commands;

import dev.rollczi.antilegs.LiteAntiLegs;
import dev.rollczi.litecommands.inject.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerArgument implements SingleArgumentHandler<Player> {

    private final LiteAntiLegs plugin;

    public PlayerArgument(LiteAntiLegs plugin) {
        this.plugin = plugin;
    }

    @Override
    public Player parse(String argument) throws ValidationCommandException {
        return Option.of(Bukkit.getPlayer(argument))
                .orThrow(() -> new ValidationCommandException(ValidationInfo.CUSTOM, plugin.getConfigManager().getPluginConfig().onFoundPlayer));
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Bukkit.getOnlinePlayers().stream()
                .map(HumanEntity::getName)
                .collect(Collectors.toList());
    }

}
