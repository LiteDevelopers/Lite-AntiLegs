/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.system;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.rollczi.antilegs.SMCAntiLegs;
import dev.rollczi.antilegs.config.PluginConfig;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.concurrent.TimeUnit;

public class CombatManager {

    private final Cache<Player, Player> lastDamagers;

    public CombatManager(SMCAntiLegs plugin) {
        PluginConfig config = plugin.getConfigManager().getPluginConfig();

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
