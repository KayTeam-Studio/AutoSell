package de.xnonymous.autosell.listener;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.chest.PlayerChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClickListener implements Listener {

    private final AutoSell autoSell;

    public InventoryClickListener(AutoSell autoSell) {
        this.autoSell = autoSell;
    }

    @EventHandler
    public void onInventoryClick(InventoryCloseEvent event) {
        if (event.getInventory().getLocation() == null)
            return;
        PlayerChest playerChest = autoSell.getPlayerChestRegistry().find(event.getInventory().getLocation());
        if (playerChest != null)
            playerChest.handle(event.getInventory().getLocation());

    }

}
