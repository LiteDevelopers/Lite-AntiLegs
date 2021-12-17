/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.listeners;

import dev.rollczi.antilegs.LiteAntiLegs;
import dev.rollczi.antilegs.system.antilegs.AntiLegsManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import panda.std.stream.PandaStream;

@RequiredArgsConstructor
public class ArmorEquipBlock implements Listener {

    private final LiteAntiLegs plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        AntiLegsManager legsManager = plugin.getAntiLegsManager();

        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            event.setCancelled(PandaStream.of(legsManager.getAntiLegs())
                    .find(antiLegs -> antiLegs.getItem().build().isSimilar(event.getCursor()))
                    .isDefined());
        }

        Inventory inventory = event.getInventory();
        ClickType click = event.getClick();

        if (click.isShiftClick() && inventory.getType() == InventoryType.CRAFTING) {
            event.setCancelled(PandaStream.of(legsManager.getAntiLegs())
                    .find(antiLegs -> antiLegs.getItem().build().isSimilar(event.getCurrentItem()))
                    .isDefined());
        }
    }

}

