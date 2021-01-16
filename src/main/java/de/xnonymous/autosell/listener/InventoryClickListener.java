package de.xnonymous.autosell.listener;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.chest.PlayerChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClickListener implements Listener {


    @EventHandler
    public void onInventoryClick(InventoryCloseEvent event) {
        if (event.getInventory().getLocation() == null)
            return;
        PlayerChest playerChest = AutoSell.getAutoSell().getPlayerChestRegistry().find(event.getInventory().getLocation());
        if (playerChest != null)
            playerChest.handle(event.getInventory().getLocation());

    }

}
