package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.kayteam.kayteamapi.command.SimpleCommand;
import org.kayteam.kayteamapi.yaml.Yaml;

public class EnableAutoSellChestDebugCommand extends SimpleCommand {

    private final AutoSell AUTOSELL;
    private final Yaml messages;

    public EnableAutoSellChestDebugCommand(AutoSell AUTOSELL) {
        super("EnableAutoSellChestDebug");
        this.AUTOSELL = AUTOSELL;
        messages = AUTOSELL.getMessages();
    }

    @Override
    public void onConsoleExecute(ConsoleCommandSender sender, String[] args) {
        messages.sendMessage(sender, "onlyPlayer");
    }

    @Override
    public void onPlayerExecute(Player sender, String[] args) {
        if (sender.hasPermission("autosell.debug")) {
            if (AUTOSELL.getPlayerChestRegistry().find(sender) != null) {
                AUTOSELL.getPlayerChestRegistry().find(sender).setDebug(!AUTOSELL.getPlayerChestRegistry().find(sender).isDebug());
                AUTOSELL.getChests().set(sender.getUniqueId() + ".debug", AUTOSELL.getPlayerChestRegistry().find(sender).isDebug());
                AUTOSELL.getChests().saveFileConfiguration();
                String status = AUTOSELL.getPlayerChestRegistry().find(sender).isDebug() ? "enabled" : "disabled";
                messages.sendMessage(sender, "debugging", new String[][]{{"%status%", status}});
            } else {
                messages.sendMessage(sender, "needChestsPlacedToDebug");
            }
        } else {
            messages.sendMessage(sender, "noPermission");
        }
    }

}