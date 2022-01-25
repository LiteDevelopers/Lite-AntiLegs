/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.listeners;

import dev.rollczi.antilegs.LiteAntiLegs;
import dev.rollczi.antilegs.config.PluginConfig;
import dev.rollczi.antilegs.system.CooldownManager;
import dev.rollczi.antilegs.system.antilegs.AntiLegs;
import dev.rollczi.antilegs.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class PlayerInteract implements Listener {

    private final LiteAntiLegs plugin;

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
                .peek(milliseconds -> {
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
                    String milliSuffix = String.format("%03d", milliseconds - TimeUnit.SECONDS.toMillis(seconds));
                    String message = config.useNoReUse;

                    message = message.replaceAll("\\{SECONDS}", String.valueOf(seconds));
                    message = message.replaceAll("\\{MILLI_SECONDS}", String.valueOf(milliseconds));
                    message = message.replaceAll("\\{MILLI_WIHOUT_SECONDS}", String.valueOf(milliSuffix));

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
                    player.setItemInHand(item);

                    event.setCancelled(true);
                    player.updateInventory();
                });
    }

}
