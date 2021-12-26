package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.kayteam.kayteamapi.command.SimpleCommand;
import org.kayteam.kayteamapi.yaml.Yaml;

public class BuyAutoSellChestCommand extends SimpleCommand{

    private final AutoSell AUTOSELL;
    private final Yaml messages;

    public BuyAutoSellChestCommand(AutoSell AUTOSELL) {
        super("BuyAutoSellChest");
        this.AUTOSELL = AUTOSELL;
        messages = AUTOSELL.getMessages();
    }

    @Override
    public void onConsoleExecute(ConsoleCommandSender sender, String[] args) {
        messages.sendMessage(sender, "onlyPlayer");
    }

    @Override
    public void onPlayerExecute(Player sender, String[] args) {
        if (sender.hasPermission("autosell.buy")) {
            if (args.length > 0) {
                String amountString = args[0];
                try {
                    int amount = Integer.parseInt(amountString);
                    if (amount > 0) {
                        double price = AUTOSELL.getPrice() * amount;
                        try {
                            if (AUTOSELL.getEconomy().has(sender, price)) {
                                sender.getInventory().addItem(new ItemBuilder(Material.CHEST, amount).setName("§aAutoSell Chest").toItemStack());
                                AUTOSELL.getEconomy().withdrawPlayer(sender, price);
                                messages.sendMessage(sender, "chestsBought", new String[][]{
                                        {"%amount%", amountString}
                                });
                            } else {
                                String need = (price - AUTOSELL.getEconomy().getBalance(sender)) + "";
                                messages.sendMessage(sender, "needMoneyToBuy", new String[][]{
                                        {"%money%",  need},
                                        {"%amount%", amountString}
                                });
                            }
                        } catch (IllegalArgumentException ignored) {
                            messages.sendMessage(sender, "inventoryFull");
                        }
                    } else {
                        messages.sendMessage(sender, "negativeNumber");
                    }
                } catch (NumberFormatException ignored) {
                    messages.sendMessage(sender, "invalidNumber", new String[][]{{"%number%", amountString}});
                }
            } else {
                messages.sendMessage(sender, "noArguments");
            }
        } else {
            messages.sendMessage(sender, "noPermission");
        }
    }

}
