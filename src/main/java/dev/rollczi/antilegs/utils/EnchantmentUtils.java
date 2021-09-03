package dev.rollczi.antilegs.utils;

import org.bukkit.enchantments.Enchantment;
import panda.std.Option;
import panda.std.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EnchantmentUtils {

    public static Option<Enchantment> getByName(String name) {
        for (Enchantment enchantment : Enchantment.values()) {
            if (!name.equalsIgnoreCase(enchantment.getName())) {
                continue;
            }

            return Option.of(enchantment);
        }

        return Option.of(Enchantment.getByName(name));
    }

    public static Map<Enchantment, Integer> deserialize(Map<String, Integer> enchantsToDeserialize) {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchantsToDeserialize.forEach((ench, level) -> getByName(ench).peek(enchantment -> enchants.put(enchantment, level)));
        return enchants;
    }

    public static Map<String, Integer> serialize(Map<Enchantment, Integer> enchantsToSerialize) {
        return enchantsToSerialize.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }

}
