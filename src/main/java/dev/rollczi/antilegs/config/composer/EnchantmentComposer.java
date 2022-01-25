/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.config.composer;

import org.bukkit.enchantments.Enchantment;
import panda.std.Option;
import panda.std.Result;

public class EnchantmentComposer implements SimpleComposer<Enchantment> {

    @Override
    public Result<Enchantment, Exception> deserialize(String enchantName) {
        for (Enchantment enchantment : Enchantment.values()) {
            if (!enchantName.equalsIgnoreCase(enchantment.getName())) {
                continue;
            }

            return Result.ok(enchantment);
        }

        return Option.of(Enchantment.getByName(enchantName))
                .toResult(() -> new RuntimeException("Can't convert '" + enchantName + "' to Enchantment."));
    }

    @Override
    public Result<String, Exception> serialize(Enchantment enchantment) {
        return Result.ok(enchantment.getName());
    }

}
