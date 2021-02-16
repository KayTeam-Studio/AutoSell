package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        AutoSell.getAutoSell().getConfigRegistry().getObjects().forEach(Config::reload);
        AutoSell.getAutoSell().reload();
        commandSender.sendMessage(AutoSell.getAutoSell().getPrefix() + "Config reloaded!");
        return false;
    }
}
