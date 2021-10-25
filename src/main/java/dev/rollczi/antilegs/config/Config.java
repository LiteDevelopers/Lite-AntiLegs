/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.config;

import dev.rollczi.antilegs.SMCAntiLegs;
import lombok.SneakyThrows;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Bukkit;
import panda.utilities.FileUtils;

import java.io.File;
import java.io.Serializable;

public class Config<T extends Serializable> {

    private final ConfigManager configManager;
    private final File file;
    private final Class<T> configurationClass;

    private T pluginConfig;

    private Config(ConfigManager configManager, Class<T> configurationClass, String fileName) {
        this.configManager = configManager;
        this.configurationClass = configurationClass;
        this.file = new File(SMCAntiLegs.getInstance().getDataFolder(), fileName);
    }

    public void loadConfig() throws Exception {
        this.pluginConfig = configManager.getCdn().load(Source.of(file), configurationClass);
    }

    @SneakyThrows
    public void saveConfig() {
        if (pluginConfig == null) {
            Bukkit.getLogger().warning("Cannot save or overwrite the contents of " + file.getName() + " (config is null)");
            return;
        }

        FileUtils.overrideFile(file, configManager.getCdn().render(pluginConfig));
    }

    public T getPluginConfig() {
        return pluginConfig;
    }

    @SneakyThrows
    public static <T extends Serializable> Config<T> create(ConfigManager configManager, Class<T> configurationClass, String fileName) {
        Config<T> config = new Config<>(configManager, configurationClass, fileName);

        if (config.file.exists()) {
            config.loadConfig();
            return config;
        }

        if (config.file.getParentFile().mkdir()) {
            System.out.println("File paths have been created.");
        }

        if (!config.file.createNewFile()) {
            System.out.println("Cannot create of " + fileName);
        }

        config.loadConfig();
        config.saveConfig();
        return config;
    }
}
