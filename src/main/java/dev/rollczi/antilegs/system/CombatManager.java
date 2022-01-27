/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.system;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.rollczi.antilegs.config.PluginConfig;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.concurrent.TimeUnit;

public class CombatManager {

    private final Cache<Player, Player> lastDamagers;

    public CombatManager(PluginConfig config) {

        this.lastDamagers = CacheBuilder
                .newBuilder()
                .expireAfterWrite(config.secondExpire, TimeUnit.SECONDS)
                .build();
    }

    public void registerNewDamager(Player player, Player damager) {
        lastDamagers.put(player, damager);
    }

    public Option<Player> getLastDamager(Player player) {
        return Option.of(lastDamagers.getIfPresent(player));
    }

}
