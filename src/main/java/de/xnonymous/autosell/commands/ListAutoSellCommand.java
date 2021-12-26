package de.xnonymous.autosell.commands;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.chest.PlayerChest;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.kayteam.kayteamapi.command.SimpleCommand;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.Objects;

public class ListAutoSellCommand extends SimpleCommand {

    private final AutoSell AUTOSELL;
    private final Yaml messages;

    public ListAutoSellCommand(AutoSell AUTOSELL) {
        super("ListAutoSellChests");
        this.AUTOSELL = AUTOSELL;
        messages = AUTOSELL.getMessages();
    }

    @Override
    public void onConsoleExecute(ConsoleCommandSender sender, String[] args) {
        messages.sendMessage(sender, "onlyPlayer");
    }

    @Override
    public void onPlayerExecute(Player sender, String[] args) {
        if (sender.hasPermission("autosell.list")) {
            PlayerChest playerChest = AUTOSELL.getPlayerChestRegistry().find(sender);
            if (playerChest != null && !playerChest.getChests().isEmpty()) {
                messages.sendMessage(sender, "list.header");
                for (Location chest : playerChest.getChests()) {
                    messages.sendMessage(sender, "list.format", new String[][]{
                            {"%world%", Objects.requireNonNull(chest.getWorld()).getName()},
                            {"%x%", chest.getBlockX() + ""},
                            {"%y%", chest.getBlockY() + ""},
                            {"%z%", chest.getBlockZ() + ""}
                    });
                }
                messages.sendMessage(sender, "list.footer");
            } else {
                messages.sendMessage(sender, "needChestsPlacedToList");
            }
        } else {
            messages.sendMessage(sender, "noPermission");
        }
    }

}