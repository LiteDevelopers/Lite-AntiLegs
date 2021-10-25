/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.config;

import dev.rollczi.antilegs.config.composer.CharacterComposer;
import dev.rollczi.antilegs.config.composer.EnchantmentComposer;
import lombok.Getter;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import org.bukkit.enchantments.Enchantment;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ConfigManager {

    private final Cdn cdn = CdnFactory
            .createYamlLike()
            .getSettings()
            .withComposer(Character.class, new CharacterComposer())
            .withComposer(Enchantment.class, new EnchantmentComposer())
            .build();

    private final Set<Config<?>> configs = new HashSet<>();

    @Getter private PluginConfig pluginConfig;

    public void loadConfigs() {
        this.pluginConfig = generate(PluginConfig.class, "config.yml");
    }

    public void saveConfigs() {
        configs.forEach(Config::saveConfig);
    }

    public <T extends Serializable> T generate(Class<T> configurationClass, String fileName) {
        Config<T> config = Config.create(this, configurationClass, fileName);
        this.configs.add(config);
        return config.getPluginConfig();
    }

    public Cdn getCdn() {
        return cdn;
    }
}
