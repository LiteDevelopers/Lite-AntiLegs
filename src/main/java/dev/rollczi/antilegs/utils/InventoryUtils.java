/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    private InventoryUtils() {}

    public static void addToInventory(Player player, ItemStack item) {
        if (hasFreeSlotFor(player.getInventory(), item)) {
            player.getInventory().addItem(item);
            return;
        }

        player.getWorld().dropItemNaturally(player.getLocation(), item);
    }

    public static boolean hasFreeSlot(Inventory inventory) {
        return inventory.firstEmpty() != -1;
    }

    public static boolean hasFreeSlotFor(Inventory inventory, ItemStack item) {
        if (hasFreeSlot(inventory)) {
            return true;
        }

        for (ItemStack itemInv : inventory.getContents()) {
            if (itemInv == null) {
                continue;
            }

            if (!itemInv.isSimilar(item)) {
                continue;
            }

            if (itemInv.getMaxStackSize() > itemInv.getAmount()) {
                return true;
            }
        }

        return false;
    }

}
