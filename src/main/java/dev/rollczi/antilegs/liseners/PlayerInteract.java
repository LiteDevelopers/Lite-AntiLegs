/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.liseners;

import dev.rollczi.antilegs.SMCAntiLegs;
import dev.rollczi.antilegs.config.PluginConfig;
import dev.rollczi.antilegs.system.CooldownManager;
import dev.rollczi.antilegs.system.antilegs.AntiLegs;
import dev.rollczi.antilegs.utils.ChatUtils;
import dev.rollczi.antilegs.utils.InventoryUtils;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class PlayerInteract implements Listener {

    private final SMCAntiLegs plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (item.getType() == Material.AIR) {
            return;
        }

        Option<AntiLegs> legsOption = PandaStream.of(plugin.getAntiLegsManager().getAntiLegs())
                .find(antiLegs -> item.isSimilar(antiLegs.getItem().build()));

        if (legsOption.isEmpty()) {
            return;
        }

        AntiLegs antiLegs = legsOption.get();
        PluginConfig config = plugin.getConfigManager().getPluginConfig();
        CooldownManager manager = plugin.getCooldownManager();

        manager.getCooldownTime(player, antiLegs)
                .peek(cooldownTime -> {
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(cooldownTime);
                    String message = config.useNoReUse.replaceAll("\\{SEC}", String.valueOf(seconds));

                    player.sendMessage(ChatUtils.color(message));
                    event.setCancelled(true);
                    player.updateInventory();
                })
                .onEmpty(() -> {
                    if (!antiLegs.use(player)) {
                        player.sendMessage(ChatUtils.color(config.useNo));
                        event.setCancelled(true);
                        player.updateInventory();
                        return;
                    }

                    manager.registerUse(player, antiLegs);
                    player.sendMessage(ChatUtils.color(config.useMessage));
                    item.setAmount(item.getAmount() - 1);
                });
    }

}
