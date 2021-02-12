package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuyAutoSellChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to execute this command");
            return false;
        }
        Player player = (Player) commandSender;
        Player target = (Bukkit.getServer().getPlayer(args[0])); // default to commandSender and check if username or amount
        int amount = 1;
        
        if (args.length > 2) {
            commandSender.sendMessage("Too many arguments!");
            return false;
    } 
        if (args.length < 1) {
            commandSender.sendMessage("Not enough arguments!");
            return false;
    }
        if (target == null) {
            commandSender.sendMessage(args[0] + " is not online!");
            return false;
    }
        if (args[1]) {
            try {
                amount = Integer.parseInt(args[1]);
        }
            catch (NumberFormatException e) {
                commandSender.sendMessage(args[1] + " is not a number");
                return false;
        }
    }

        try {
            if (AutoSell.getAutoSell().getEcon().has(player, AutoSell.getAutoSell().getPrice())) {
                target.getInventory().addItem(new ItemBuilder(Material.CHEST).setName("§aAutoSell Chest").toItemStack());
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
