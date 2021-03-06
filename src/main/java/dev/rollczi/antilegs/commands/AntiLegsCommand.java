/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.commands;

import dev.rollczi.antilegs.LiteAntiLegs;
import dev.rollczi.antilegs.config.ConfigurationManager;
import dev.rollczi.antilegs.config.PluginConfig;
import dev.rollczi.antilegs.system.antilegs.AntiLegs;
import dev.rollczi.antilegs.system.antilegs.AntiLegsManager;
import dev.rollczi.antilegs.system.antilegs.StandardAntiLegs;
import dev.rollczi.antilegs.utils.ChatUtils;
import dev.rollczi.antilegs.utils.InventoryUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Required;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import dev.rollczi.litecommands.platform.LiteSender;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import net.dzikoysk.cdn.CdnException;
import org.bukkit.entity.Player;
import panda.std.Blank;
import panda.std.Result;

@Section(route = "antilegs", aliases = {"antynogi"})
@Permission("dev.rollczi.antilegs")
@UsageMessage("&cPrawidłowe użycie &8» &7/antynogi give|give -other|reload")
public final class AntiLegsCommand {

    @Section(route = "give")
    @Permission("dev.rollczi.antilegs.give")
    public static class Give {

        private final LiteAntiLegs plugin;

        public Give(LiteAntiLegs plugin) {
            this.plugin = plugin;
        }

        @Execute @Required(1)
        @UsageMessage("&cPrawidłowe użycie &8» &7/antynogi give <gracz>")
        public void executeGive(LiteSender sender, @Arg(0) Player player) {
            AntiLegsManager manager = plugin.getAntiLegsManager();
            AntiLegs antiLegs = manager.getAntiLegs(StandardAntiLegs.class)
                    .orThrow(() -> new ValidationCommandException(ValidationInfo.CUSTOM, "&cBłąd: Standardowa implementacja nie została zarejestrowana."));

            give(sender, player, antiLegs);
        }

        @Execute(route = "-other") @Required(2)
        @UsageMessage("&cPrawidłowe użycie &8» &7/antynogi give -other <antilegs> <gracz>")
        public void executeGiveOther(LiteSender sender, @Arg(0) AntiLegs antiLegs, @Arg(1) Player player) {
            give(sender, player, antiLegs);
        }

        @IgnoreMethod
        private void give(LiteSender sender, Player player, AntiLegs antiLegs) {
            PluginConfig config = plugin.getConfigManager().getPluginConfig();
            String message = config.giveAntiLegs.replaceAll("\\{TYPE}", antiLegs.getName());

            sender.sendMessage(config.giveAntiLegsAdmin);
            player.sendMessage(ChatUtils.color(message));
            InventoryUtils.addToInventory(player, antiLegs.getItem().build());
        }

    }

    @Execute(route = "reload")
    @Permission("dev.rollczi.antilegs.reload")
    public void reload(LiteSender liteSender, ConfigurationManager manager) {
        Result<Blank, CdnException> result = manager.loadConfigs();

        if (result.isOk()) {
            PluginConfig config = manager.getPluginConfig();

            liteSender.sendMessage(config.reload);
            return;
        }

        CdnException error = result.getError();

        liteSender.sendMessage("&cInternal Error: " + error.getMessage());
        liteSender.sendMessage("&c(See console logs)");
        error.printStackTrace();
    }

}
