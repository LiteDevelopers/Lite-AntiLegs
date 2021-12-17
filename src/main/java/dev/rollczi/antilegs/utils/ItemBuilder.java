/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class ItemBuilder {

    private final ItemStack itemStack;

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material, int amount, int data) {
        this.itemStack = new ItemStack(material, amount, (short) data);
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(new ItemStack(itemStack));
    }

    public static ItemBuilder of(ItemBuilder itemBuilder) {
        return new ItemBuilder(itemBuilder.build());
    }

    public static ItemBuilder none() {
        return new ItemBuilder(Material.STONE).setName("&7None");
    }

    public ItemBuilder setType(Material type) {
        this.itemStack.setType(type);
        return this;
    }

    public ItemBuilder setName(String name) {
        return onMeta(meta -> meta.setDisplayName(ChatUtils.color(name)));
    }

    public ItemBuilder setLore(List<String> lore) {
        return onMeta(meta -> meta.setLore(ChatUtils.color(lore)));
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach(this::addEnchantment);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        return onMeta(meta -> meta.addEnchant(enchantment, level, true));
    }

    public ItemBuilder setEnchantment(Enchantment enchantment, int level) {
        return onMeta(meta -> {
            this.removeEnchantments();
            meta.addEnchant(enchantment, level, true);
        });
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        return onMeta(meta -> meta.removeEnchant(enchantment));
    }

    public ItemBuilder removeEnchantments() {
        itemStack.getEnchantments().keySet().forEach(this::removeEnchantment);
        return this;
    }

    public ItemBuilder setHideAttributes(boolean hide) {
        return setItemFlag(ItemFlag.HIDE_ATTRIBUTES, hide);
    }

    public ItemBuilder setItemFlag(ItemFlag itemFlag, boolean hide) {
        return onMeta(meta -> {
            if (hide) {
                meta.addItemFlags(itemFlag);
                return;
            }

            meta.removeItemFlags(itemFlag);
        });
    }

    public ItemBuilder setItemFlags(List<ItemFlag> flags) {
        for (ItemFlag flag : flags) {
            setItemFlag(flag, true);
        }

        return this;
    }

    public ItemStack build() {
        return new ItemStack(itemStack);
    }

    public ItemBuilder onMeta(Consumer<ItemMeta> metaConsumer) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (meta == null) {
            return this;
        }

        metaConsumer.accept(meta);
        this.itemStack.setItemMeta(meta);
        return this;
    }

}
