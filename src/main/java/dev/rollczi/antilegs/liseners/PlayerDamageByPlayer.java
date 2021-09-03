/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.liseners;

import dev.rollczi.antilegs.SMCAntiLegs;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@RequiredArgsConstructor
public class PlayerDamageByPlayer implements Listener {

    private final SMCAntiLegs plugin;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (!(entity instanceof Player) || !(damager instanceof Player)) {
            return;
        }

        plugin.getCombatManager().registerNewDamager((Player) entity, (Player) damager);
    }

}
