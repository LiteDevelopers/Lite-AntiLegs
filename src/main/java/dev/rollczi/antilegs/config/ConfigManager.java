/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.config;

import dev.rollczi.antilegs.LiteAntiLegs;
import dev.rollczi.antilegs.config.composer.CharacterComposer;
import dev.rollczi.antilegs.config.composer.EnchantmentComposer;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.enchantments.Enchantment;

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ConfigManager {

    private final LiteAntiLegs plugin;
    private final Cdn cdn = CdnFactory
            .createYamlLike()
            .getSettings()
            .withComposer(Character.class, new CharacterComposer())
            .withComposer(Enchantment.class, new EnchantmentComposer())
            .build();

    private final Set<Object> configs = new HashSet<>();

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
        T load = cdn.load(Source.of(file), configurationClass);

        cdn.render(load, file);

        this.configs.add(load);
        return load;
    }

}
