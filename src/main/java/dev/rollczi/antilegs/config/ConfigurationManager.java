/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.config;

import dev.rollczi.antilegs.config.composer.CharacterComposer;
import dev.rollczi.antilegs.config.composer.EnchantmentComposer;
import lombok.Getter;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnException;
import net.dzikoysk.cdn.CdnFactory;
import org.bukkit.enchantments.Enchantment;
import panda.std.Blank;
import panda.std.Result;

import java.io.File;

public class ConfigurationManager {

    private final Cdn cdn = CdnFactory
            .createYamlLike()
            .getSettings()
            .withComposer(Character.class, new CharacterComposer())
            .withComposer(Enchantment.class, new EnchantmentComposer())
            .build();

    @Getter private final PluginConfig pluginConfig;

    public ConfigurationManager(File dataFolder) {
        this.pluginConfig = new PluginConfig(dataFolder, "config.yml");
    }

    public Result<Blank, CdnException> loadConfigs() {
        return Result.<CdnException>ok()
                .flatMap(blank -> this.load(this.pluginConfig))
                .mapToBlank();
    }

    public <T extends ConfigWithResource> Result<T, CdnException> load(T config) {
        return cdn.load(config.getResource(), config)
                .flatMap(load -> cdn.render(config, config.getResource()))
                .map(render -> config);
    }

}
