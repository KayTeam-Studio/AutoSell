package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.chest.PlayerChest;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListAutoSellCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to execute this command");
            return false;
        }
        Player player = (Player) commandSender;

        PlayerChest playerChest = AutoSell.getAutoSell().getPlayerChestManager().find(player);
        if (playerChest != null && !playerChest.getChests().isEmpty()) {
            player.sendMessage(AutoSell.getAutoSell().getPrefix() + "Here:");
            for (Location chest : playerChest.getChests()) {
                player.sendMessage(AutoSell.getAutoSell().getPrefix() + "X:" + chest.getBlockX() + " Y:" + chest.getBlockY() + " Z:" + chest.getBlockZ());
            }
        } else
            player.sendMessage(AutoSell.getAutoSell().getPrefix() + "You must have placed at least one chest");

        return false;
    }
}
