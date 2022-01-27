/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs;

import dev.rollczi.antilegs.commands.AntiLegsArgument;
import dev.rollczi.antilegs.commands.AntiLegsCommand;
import dev.rollczi.antilegs.commands.LitePermissionMessage;
import dev.rollczi.antilegs.commands.PlayerArgument;
import dev.rollczi.antilegs.config.ConfigurationManager;
import dev.rollczi.antilegs.config.PluginConfig;
import dev.rollczi.antilegs.listeners.ArmorEquipBlock;
import dev.rollczi.antilegs.listeners.PlayerDamageByPlayer;
import dev.rollczi.antilegs.listeners.PlayerInteract;
import dev.rollczi.antilegs.system.CooldownManager;
import dev.rollczi.antilegs.system.antilegs.AntiLegs;
import dev.rollczi.antilegs.system.antilegs.AntiLegsManager;
import dev.rollczi.antilegs.system.CombatManager;
import dev.rollczi.antilegs.system.antilegs.StandardAntiLegs;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.valid.ValidationInfo;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class LiteAntiLegs extends JavaPlugin {

    @Getter private static LiteAntiLegs instance;

    @Getter private ConfigurationManager configManager;
    @Getter private CombatManager combatManager;
    @Getter private CooldownManager cooldownManager;
    @Getter private AntiLegsManager antiLegsManager;
    @Getter private LiteCommands liteCommands;

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigurationManager(this.getDataFolder());
        this.configManager.loadConfigs(); // load data from config file

        PluginConfig config = this.configManager.getPluginConfig();

        this.combatManager = new CombatManager(config);
        this.cooldownManager = new CooldownManager();
        this.antiLegsManager = new AntiLegsManager();
        this.antiLegsManager.registerAntiLeg(StandardAntiLegs.create(config));

        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "lite-antilegs")
                .bind(LiteAntiLegs.class, () -> this)
                .bind(ConfigurationManager.class, () -> configManager)

                .argument(Player.class, new PlayerArgument(this))
                .argument(AntiLegs.class, new AntiLegsArgument(this))

                .message(ValidationInfo.NO_PERMISSION, new LitePermissionMessage(config))
                .command(AntiLegsCommand.class)
                .register();

        PluginManager pluginManager = Bukkit.getPluginManager();
        Stream.of(
                new PlayerDamageByPlayer(this),
                new PlayerInteract(this),
                new ArmorEquipBlock(this)
        ).forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        liteCommands.getPlatformManager().unregisterCommands();
    }

}
