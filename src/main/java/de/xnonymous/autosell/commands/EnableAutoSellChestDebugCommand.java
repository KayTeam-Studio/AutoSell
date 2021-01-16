package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnableAutoSellChestDebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to execute this command");
            return false;
        }
        Player player = (Player) commandSender;

        if (AutoSell.getAutoSell().getPlayerChestRegistry().find(player) == null) {
            player.sendMessage(AutoSell.getAutoSell().getPrefix() + "You must have placed at least one chest to enable debugging");
            return false;
        }

        AutoSell.getAutoSell().getPlayerChestRegistry().find(player).setDebug(!AutoSell.getAutoSell().getPlayerChestRegistry().find(player).isDebug());
        AutoSell.getAutoSell().getChestConfig().getCfg().set(player.getUniqueId().toString() + ".debug", AutoSell.getAutoSell().getPlayerChestRegistry().find(player).isDebug());
        AutoSell.getAutoSell().getChestConfig().save();
        player.sendMessage(AutoSell.getAutoSell().getPrefix() + "Debugging " + (AutoSell.getAutoSell().getPlayerChestRegistry().find(player).isDebug() ? "enabled" : "disabled"));

        return false;
    }
}
