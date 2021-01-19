package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveAutoSellChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to execute this command");
            return false;
        }
        Player player = (Player) commandSender;

        try {
            if (AutoSell.getAutoSell().getEcon().has(player, AutoSell.getAutoSell().getPrice())) {
                player.getInventory().addItem(new ItemBuilder(Material.CHEST).setName("§aAutoSell Chest").toItemStack());
                AutoSell.getAutoSell().getEcon().withdrawPlayer(player, AutoSell.getAutoSell().getPrice());
                player.sendMessage(AutoSell.getAutoSell().getPrefix() + "Happy selling!");
            } else {
                player.sendMessage(AutoSell.getAutoSell().getPrefix() + "You need " + (AutoSell.getAutoSell().getPrice() - AutoSell.getAutoSell().getEcon().getBalance(player)) + " more cash to buy a Autosell chest!");
            }
        } catch (Exception ignored) {
            player.sendMessage(AutoSell.getAutoSell().getPrefix() + "Your inventory is full");
        }

        return false;
    }
}
