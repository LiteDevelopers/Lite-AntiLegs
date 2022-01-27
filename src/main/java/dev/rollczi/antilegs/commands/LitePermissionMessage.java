/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.commands;

import dev.rollczi.antilegs.config.PluginConfig;
import dev.rollczi.litecommands.valid.messages.LiteMessage;
import dev.rollczi.litecommands.valid.messages.MessageInfoContext;
import panda.utilities.text.Joiner;

public class LitePermissionMessage implements LiteMessage {

    private final PluginConfig config;

    public LitePermissionMessage(PluginConfig config) {
        this.config = config;
    }

    @Override
    public String message(MessageInfoContext messageInfoContext) {
        String permissions = Joiner
                .on(", ")
                .join(messageInfoContext.getMissingPermissions())
                .toString();

        return config.noPermission.replace("{PERMISSIONS}", permissions);
    }

}
