/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.system;

import dev.rollczi.antilegs.system.antilegs.AntiLegs;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CooldownManager {

    private final Map<Player, Map<AntiLegs, Long>> cooldowns = Collections.synchronizedMap(new HashMap<>());

    public void registerUse(Player player, AntiLegs antiLegs) {
        Map<AntiLegs, Long> lastUse = getCacheOrCreate(player);

        lastUse.put(antiLegs, System.currentTimeMillis());
        cooldowns.put(player, lastUse);
    }

    public Option<Long> getCooldownTime(Player player, AntiLegs antiLegs) {

        long millis = System.currentTimeMillis() - getCacheOrCreate(player).getOrDefault(antiLegs, 0L);
        long cooldown = TimeUnit.SECONDS.toMillis(antiLegs.getCooldown());

        return (millis >= cooldown ? Option.none() : Option.of(cooldown - millis));
    }

    private Map<AntiLegs, Long> getCacheOrCreate(Player player) {
        return cooldowns.getOrDefault(player, Collections.synchronizedMap(new HashMap<>()));
    }

}
