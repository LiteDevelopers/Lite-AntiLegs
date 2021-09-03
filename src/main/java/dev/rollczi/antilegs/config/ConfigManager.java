/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.config;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ConfigManager {

    private final Set<Config<?>> configs = new HashSet<>();

    @Getter private PluginConfig pluginConfig;

    public void loadConfigs() {
        this.pluginConfig = generate(PluginConfig.class, "config.yml");
    }

    public void saveConfigs() {
        configs.forEach(Config::saveConfig);
    }

    public <T extends Serializable> T generate(Class<T> configurationClass, String fileName) {
        Config<T> config = Config.create(configurationClass, fileName);
        this.configs.add(config);
        return config.getPluginConfig();
    }

}
