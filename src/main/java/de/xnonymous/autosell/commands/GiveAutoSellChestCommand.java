package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveAutoSellChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length > 2) {
            commandSender.sendMessage("Too many arguments!");
            return false;
        }
        if (args.length < 1) {
            commandSender.sendMessage("Not enough arguments!");
            return false;
        }

        Player player = (Player) commandSender;
        Player target = (Bukkit.getServer().getPlayer(args[0]));
        int amount = 1;

        if (target == null) {
            commandSender.sendMessage(args[0] + " is not online!");
            return true;
        }
        if (args.length == 2) {
            if (args[1].matches("-?(0|[1-9]\\d*)")) {
                amount = Integer.parseInt(args[1]);
            } else {
                commandSender.sendMessage(args[1] + " is not a number");
                return false;
            }
        }
        if (Integer.signum(amount) != 1) {
            commandSender.sendMessage("Amount must be greater than 0");
            return true;
        }

        try {
            target.getInventory().addItem(new ItemBuilder(Material.CHEST, amount).setName("§aAutoSell Chest").toItemStack());
            target.sendMessage(AutoSell.getAutoSell().getPrefix() + "Happy selling!");
            return true;
        } catch (Exception ignored) {
            player.sendMessage(AutoSell.getAutoSell().getPrefix() + "The inventory of " + args[0] + " is full");
            return true;
        }
    }
}
