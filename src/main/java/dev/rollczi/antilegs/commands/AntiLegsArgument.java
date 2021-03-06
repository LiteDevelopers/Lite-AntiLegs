/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.commands;

import dev.rollczi.antilegs.LiteAntiLegs;
import dev.rollczi.antilegs.system.antilegs.AntiLegs;
import dev.rollczi.antilegs.system.antilegs.AntiLegsManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;

import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("antilegs_type")
public class AntiLegsArgument implements SingleArgumentHandler<AntiLegs> {

    private final LiteAntiLegs plugin;

    public AntiLegsArgument(LiteAntiLegs plugin) {
        this.plugin = plugin;
    }

    @Override
    public AntiLegs parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        AntiLegsManager manager = plugin.getAntiLegsManager();
        return manager.getAntiLegs(argument)
                .orThrow(() -> new ValidationCommandException(ValidationInfo.CUSTOM, "&cBłąd: Standardowa implementacja nie została zarejestrowana."));
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return plugin.getAntiLegsManager().getAntiLegs().stream()
                .map(AntiLegs::getName)
                .collect(Collectors.toList());
    }

}
