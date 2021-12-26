package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.kayteam.kayteamapi.command.SimpleCommand;
import org.kayteam.kayteamapi.yaml.Yaml;

public class GiveAutoSellChestCommand extends SimpleCommand {

    private final AutoSell AUTOSELL;
    private final Yaml messages;

    public GiveAutoSellChestCommand(AutoSell AUTOSELL) {
        super("GiveAutoSellChest");
        this.AUTOSELL = AUTOSELL;
        messages = AUTOSELL.getMessages();
    }

    @Override
    public void onConsoleExecute(ConsoleCommandSender sender, String[] args) {
        if (args.length > 0) {
            String targetName = args[0];
            Player target = AUTOSELL.getServer().getPlayerExact(targetName);
            int amount;
            if (target != null) {
                if (args.length > 1) {
                    String amountString = args[1];
                    try {
                        amount = Integer.parseInt(amountString);
                        if (amount > 0) {
                            try {
                                target.getInventory().addItem(new ItemBuilder(Material.CHEST, amount).setName("§aAutoSell Chest").toItemStack());
                                messages.sendMessage(sender, "give");
                            } catch (IllegalArgumentException ignored) {
                                messages.sendMessage(sender, "inventoryFullOther", new String[][]{{"%player%", targetName}});
                            }
                        } else {
                            messages.sendMessage(sender, "negativeNumber");
                        }
                    } catch (NumberFormatException ignored) {
                        messages.sendMessage(sender, "invalidNumber", new String[][]{{"%number%", amountString}});
                    }
                }
            } else {
                messages.sendMessage(sender, "playerIsOffline", new String[][]{{"%player%", args[0]}});
            }
        } else {
            messages.sendMessage(sender, "noArguments");
        }
    }

    @Override
    public void onPlayerExecute(Player sender, String[] args) {
        if (sender.hasPermission("autosell.give")) {
            if (args.length > 0) {
                String targetName = args[0];
                Player target = AUTOSELL.getServer().getPlayerExact(targetName);
                int amount;
                if (target != null) {
                    if (args.length > 1) {
                        String amountString = args[1];
                        try {
                            amount = Integer.parseInt(amountString);
                            if (amount > 0) {
                                try {
                                    target.getInventory().addItem(new ItemBuilder(Material.CHEST, amount).setName("§aAutoSell Chest").toItemStack());
                                    messages.sendMessage(sender, "gived");
                                } catch (IllegalArgumentException ignored) {
                                    messages.sendMessage(sender, "inventoryFullOther", new String[][]{{"%player%", targetName}});
                                }
                            } else {
                                messages.sendMessage(sender, "negativeNumber");
                            }
                        } catch (NumberFormatException ignored) {
                            messages.sendMessage(sender, "invalidNumber", new String[][]{{"%number%", amountString}});
                        }
                    }
                } else {
                    messages.sendMessage(sender, "playerIsOffline", new String[][]{{"%player%", args[0]}});
                }
            } else {
                messages.sendMessage(sender, "noArguments");
            }
        } else {
            messages.sendMessage(sender, "noPermission");
        }
    }

}