/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs;

import dev.rollczi.antilegs.commands.AntiLegsCommand;
import dev.rollczi.antilegs.commands.PluginBind;
import dev.rollczi.antilegs.config.ConfigManager;
import dev.rollczi.antilegs.liseners.ArmorEquipBlock;
import dev.rollczi.antilegs.liseners.PlayerDamageByPlayer;
import dev.rollczi.antilegs.liseners.PlayerInteract;
import dev.rollczi.antilegs.system.CooldownManager;
import dev.rollczi.antilegs.system.antilegs.AntiLegsManager;
import dev.rollczi.antilegs.system.CombatManager;
import dev.rollczi.antilegs.system.antilegs.StandardAntiLegs;
import lombok.Getter;
import net.dzikoysk.funnycommands.FunnyCommands;
import net.dzikoysk.funnycommands.resources.types.PlayerType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class SMCAntiLegs extends JavaPlugin {

    @Getter private static SMCAntiLegs instance;

    @Getter private ConfigManager configManager;
    @Getter private CombatManager combatManager;
    @Getter private CooldownManager cooldownManager;
    @Getter private AntiLegsManager antiLegsManager;
    @Getter private FunnyCommands funnyCommands;

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager();
        this.configManager.loadConfigs(); // load data from config file
        this.configManager.saveConfigs(); // add new variables to the config file if they are missing
        this.combatManager = new CombatManager(this);
        this.cooldownManager = new CooldownManager();
        this.antiLegsManager = new AntiLegsManager();
        this.antiLegsManager.registerAntiLeg(StandardAntiLegs.create());
        this.funnyCommands = FunnyCommands.configuration(() -> this)
                .registerDefaultComponents()
                .type(new PlayerType(super.getServer()))
                .bind(new PluginBind(this))
                .commands(AntiLegsCommand.class)
                .install();

        PluginManager pluginManager = Bukkit.getPluginManager();
        Stream.of(
                new PlayerDamageByPlayer(this),
                new PlayerInteract(this),
                new ArmorEquipBlock(this)
        ).forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        funnyCommands.dispose();
    }

}
