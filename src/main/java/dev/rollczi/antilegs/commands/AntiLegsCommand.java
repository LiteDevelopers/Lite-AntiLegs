/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.commands;

import dev.rollczi.antilegs.SMCAntiLegs;
import dev.rollczi.antilegs.config.PluginConfig;
import dev.rollczi.antilegs.system.antilegs.AntiLegs;
import dev.rollczi.antilegs.system.antilegs.AntiLegsManager;
import dev.rollczi.antilegs.system.antilegs.StandardImplAntiLegs;
import dev.rollczi.antilegs.utils.ChatUtils;
import dev.rollczi.antilegs.utils.InventoryUtils;
import net.dzikoysk.funnycommands.resources.ValidationException;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@FunnyComponent
public final class AntiLegsCommand {

    @FunnyCommand(
            name = "antilegs",
            aliases = {"smc-antilegs", "antynogi"},
            permission = "dev.rollczi.antilegs",
            acceptsExceeded = true
    )
    public void execute(SMCAntiLegs plugin, CommandSender sender, String[] args) {
        PluginConfig config = plugin.getConfigManager().getPluginConfig();

        if (args.length == 0 || !args[0].equalsIgnoreCase("give")) {
            sender.sendMessage(ChatUtils.color("&a/antynogi give [gracz]"));
            return;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            Player player = Bukkit.getPlayer(args[1]);

            if (player == null) {
                throw new ValidationException(config.onFoundPlayer);
            }

            give(plugin, player);
            return;
        }

        if (!(sender instanceof Player)) {
            throw new ValidationException(config.commandOnlyPlayer);
        }

        Player player = (Player) sender;
        give(plugin, player);
    }

    private void give(SMCAntiLegs plugin, Player player) {
        PluginConfig config = plugin.getConfigManager().getPluginConfig();
        AntiLegsManager manager = plugin.getAntiLegsManager();
        AntiLegs antiLegs = manager.getAntiLegs(StandardImplAntiLegs.class)
                .orThrow(() -> new ValidationException("&cBłąd: Standardowa implementacja nie została zarejestrowana."));

        String message = config.giveAntiLegs.replaceAll("\\{TYPE}", antiLegs.getName());

        player.sendMessage(ChatUtils.color(message));
        InventoryUtils.addToInventory(player, antiLegs.getItem().build());
    }

}
