/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.config.composer;

import org.bukkit.enchantments.Enchantment;
import panda.std.Option;

public class EnchantmentComposer implements SimpleComposer<Enchantment> {

    @Override
    public Enchantment deserialize(String enchantName) {
        for (Enchantment enchantment : Enchantment.values()) {
            if (!enchantName.equalsIgnoreCase(enchantment.getName())) {
                continue;
            }

            return enchantment;
        }

        return Option.of(Enchantment.getByName(enchantName))
                .orThrow(() -> new RuntimeException("Can't convert '" + enchantName + "' to Enchantment."));
    }

    @Override
    public String serialize(Enchantment enchantment) {
        return enchantment.getName();
    }

}
