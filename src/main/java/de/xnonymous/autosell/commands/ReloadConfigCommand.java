package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.kayteam.kayteamapi.command.SimpleCommand;
import org.kayteam.kayteamapi.yaml.Yaml;

public class ReloadConfigCommand extends SimpleCommand {

    private final AutoSell AUTOSELL;
    private final Yaml messages;

    public ReloadConfigCommand(AutoSell AUTOSELL) {
        super("ReloadAutoSellConfig");
        this.AUTOSELL = AUTOSELL;
        messages = AUTOSELL.getMessages();
    }

    @Override
    public void onConsoleExecute(ConsoleCommandSender sender, String[] args) {
        AUTOSELL.onReload();
        messages.sendMessage(sender, "reloaded");
    }

    @Override
    public void onPlayerExecute(Player sender, String[] args) {
        if (sender.hasPermission("autosell.reload")) {
            AUTOSELL.onReload();
            messages.sendMessage(sender, "reloaded");
        } else {
            messages.sendMessage(sender, "noPermission");
        }
    }

}
