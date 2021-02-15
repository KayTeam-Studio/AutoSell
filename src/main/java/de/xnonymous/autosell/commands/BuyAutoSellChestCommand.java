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
        Player target = player;
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
        if (args[0].matches("-?(0|[1-9]\\d*)")) {
            amount = Integer.parseInt(args[0]);
        } else {
            target = (Bukkit.getServer().getPlayer(args[0]));
        }
        if (args[1].matches("-?(0|[1-9]\\d*)")) {
            amount = Integer.parseInt(args[1]);
        } else {
            commandSender.sendMessage(args[1] + " is not a number");
            return false;
        }
        
        int price = AutoSell.getAutoSell().getPrice() * amount;

        try {
            if (AutoSell.getAutoSell().getEcon().has(player, price)) {
                target.getInventory().addItem(new ItemBuilder(Material.CHEST, amount).setName("§aAutoSell Chest").toItemStack());
                AutoSell.getAutoSell().getEcon().withdrawPlayer(player, price);
                if (target.getName() == player.getName()) {
                    target.sendMessage(AutoSell.getAutoSell().getPrefix() + "Happy selling!");
                } else {
                    target.sendMessage(AutoSell.getAutoSell().getPrefix() + "Happy selling! " + player.getName() + " just bought you " + amount + " Autosell chest!");
                    player.sendMessage(AutoSell.getAutoSell().getPrefix() + "You just bought " + target.getName() + " " + amount + " Autosell chest!");
                }
            } else {
                player.sendMessage(AutoSell.getAutoSell().getPrefix() + "You need " + (price - AutoSell.getAutoSell().getEcon().getBalance(player)) + " more cash to buy " + amount + " Autosell chest!");
            }
        } catch (Exception ignored) {
            if (target.getName() == player.getName()) {
                    target.sendMessage(AutoSell.getAutoSell().getPrefix() + "Your inventory is full");
                } else {
                    player.sendMessage(AutoSell.getAutoSell().getPrefix() + "The inventory of " + args[0] + " is full");
                }
        }

        return false;
    }
}
