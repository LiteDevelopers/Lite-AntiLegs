/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.config;

import dev.rollczi.antilegs.LiteAntiLegs;
import dev.rollczi.antilegs.config.composer.CharacterComposer;
import dev.rollczi.antilegs.config.composer.EnchantmentComposer;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import panda.std.function.ThrowingFunction;

import java.io.File;
import java.io.Serializable;

public class ConfigManager {

    private final LiteAntiLegs plugin;
    private final Cdn cdn = CdnFactory
            .createYamlLike()
            .getSettings()
            .withComposer(Character.class, new CharacterComposer())
            .withComposer(Enchantment.class, new EnchantmentComposer())
            .build();

    @Getter private PluginConfig pluginConfig;

    public ConfigManager(LiteAntiLegs plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        this.pluginConfig = generate(PluginConfig.class, "config.yml");
    }

    @SneakyThrows
    public <T extends Serializable> T generate(Class<T> configurationClass, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);

        if (!plugin.getDataFolder().exists()) {
            if (!plugin.getDataFolder().mkdir()) {
                Bukkit.getLogger().warning("Can't create data folder!");
            }
        }

        Resource resource = Source.of(file);
        T load = cdn.load(resource, configurationClass)
                .orElseThrow(ThrowingFunction.identity());

        cdn.render(load, resource);

        return load;
    }

}
