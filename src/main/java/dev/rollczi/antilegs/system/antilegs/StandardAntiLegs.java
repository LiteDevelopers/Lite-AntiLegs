/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.system.antilegs;

import dev.rollczi.antilegs.LiteAntiLegs;
import dev.rollczi.antilegs.config.PluginConfig;
import dev.rollczi.antilegs.system.CombatManager;
import dev.rollczi.antilegs.utils.ItemBuilder;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import panda.std.Option;

@RequiredArgsConstructor
@Getter
@Setter
public class StandardAntiLegs implements AntiLegs {

    private final String name = "standard";
    private ItemBuilder item = ItemBuilder.none();
    private double distance;
    private int cooldown;

    @Override
    public ItemBuilder getItem() {
        return ItemBuilder.of(item);
    }

    @Override
    public boolean use(Player player) {
        LiteAntiLegs plugin = LiteAntiLegs.getInstance();
        CombatManager combatManager = plugin.getCombatManager();

        Option<Player> damagerOp = combatManager.getLastDamager(player);

        if (damagerOp.isEmpty()) {
            return false;
        }

        Player damager = damagerOp.get();
        Location to = damager.getLocation().clone();
        double actualDistance = to.distance(player.getLocation());

        if (this.distance < actualDistance) {
            return false;
        }

        player.teleport(to);
        return true;
    }

    public static StandardAntiLegs create(PluginConfig config) {
        PluginConfig.ItemConfig itemConfig = config.item;
        StandardAntiLegs antiLegs = new StandardAntiLegs();
        ItemBuilder builder = new ItemBuilder(itemConfig.material, 1, itemConfig.data)
                .setLore(itemConfig.lore)
                .setName(itemConfig.name)
                .setItemFlags(itemConfig.itemFlags)
                .addEnchantments(itemConfig.enchants);

        antiLegs.setItem(builder);
        antiLegs.setDistance(config.distance);
        antiLegs.setCooldown(config.secondCooldown);

        ItemStack item = antiLegs.getItem().build();
        ShapedRecipe recipe = new ShapedRecipe(item);

        recipe.shape("123", "456", "789");
        config.crafting.entrySet().stream()
                .filter(entry -> entry.getValue() != Material.AIR)
                .forEach(entry -> recipe.setIngredient(entry.getKey(), entry.getValue()));

        Bukkit.getServer().addRecipe(recipe);
        return antiLegs;
    }

}

