/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.config;

import dev.rollczi.antilegs.SMCAntiLegs;
import lombok.SneakyThrows;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import panda.utilities.FileUtils;

import java.io.File;
import java.io.Serializable;

public class Config<T extends Serializable> {

    private final Cdn cdn = CdnFactory
            .createYamlLike()
            .getSettings()
            .withComposer(Character.class, Object::toString, (string) -> {
                if (string.length() != 1) {
                    throw new IllegalArgumentException("Ciąg znaków \"" + string + "\" jest niepoprawny! Oczekiwana długość to 1.");
                }

                return string.charAt(0);
            })
            .withComposer(Enchantment.class, Enchantment::getName, Enchantment::getByName)
            .build();

    private final File file;
    private final Class<T> configurationClass;

    private T pluginConfig;

    private Config(Class<T> configurationClass, String fileName) {
        this.configurationClass = configurationClass;
        this.file = new File(SMCAntiLegs.getInstance().getDataFolder(), fileName);
    }

    public void loadConfig() throws Exception {
        this.pluginConfig = cdn.load(file, configurationClass);
    }

    @SneakyThrows
    public void saveConfig() {
        if (pluginConfig == null) {
            Bukkit.getLogger().warning("Cannot save or overwrite the contents of " + file.getName() + " (config is null)");
            return;
        }

        FileUtils.overrideFile(file, cdn.render(pluginConfig));
    }

    public T getPluginConfig() {
        return pluginConfig;
    }

    @SneakyThrows
    public static <T extends Serializable> Config<T> create(Class<T> configurationClass, String fileName) {
        Config<T> config = new Config<>(configurationClass, fileName);

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
