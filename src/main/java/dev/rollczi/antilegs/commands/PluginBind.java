/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.commands;

import dev.rollczi.antilegs.SMCAntiLegs;
import net.dzikoysk.funnycommands.resources.Bind;
import org.panda_lang.utilities.inject.Resources;

public class PluginBind implements Bind {

    private final SMCAntiLegs plugin;

    public PluginBind(SMCAntiLegs plugin) {
        this.plugin = plugin;
    }

    @Override
    public void accept(Resources resources) {
        resources.on(SMCAntiLegs.class).assignHandler((property, annotation, objects) -> plugin);
    }

}
