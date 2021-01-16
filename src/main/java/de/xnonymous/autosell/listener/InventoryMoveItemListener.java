package de.xnonymous.autosell.listener;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.chest.PlayerChest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import java.util.ArrayList;

public class InventoryMoveItemListener implements Listener {

    private final ArrayList<Location> locations = new ArrayList<>();

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        if (event.getDestination().getLocation() != null) {
            Location location = event.getDestination().getLocation();
            if (locations.contains(location))
                return;
            PlayerChest playerChest = AutoSell.getAutoSell().getPlayerChestRegistry().find(location);
            if (playerChest == null)
                return;
            locations.add(location);
            Bukkit.getScheduler().runTaskLater(AutoSell.getAutoSell(), () -> {
                playerChest.handle(location);
                locations.remove(location);
            }, 5 * 20L);
        }
    }

}
