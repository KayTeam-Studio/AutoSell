package de.xnonymous.autosell.listener;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.chest.PlayerChest;
import de.xnonymous.autosell.config.impl.ChestConfig;
import de.xnonymous.autosell.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.Objects;


public class PlayerBlockBreakListener implements Listener {

    private final AutoSell autoSell;

    public PlayerBlockBreakListener(AutoSell autoSell) {
        this.autoSell = autoSell;
    }

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            if (event.getBlock().getType() == Material.CHEST) {
                Location location = event.getBlock().getLocation();
                PlayerChest playerChest = autoSell.getPlayerChestRegistry().find(location);
                if (playerChest != null) {
                    playerChest.remove(location);
                    event.setDropItems(false);
                    Objects.requireNonNull(location.getWorld()).dropItem(location, new ItemBuilder(Material.CHEST).setName("§aAutoSell Chest").toItemStack());
                    Yaml chests = autoSell.getChests();
                    chests.set(playerChest.getOfflinePlayer().getUniqueId().toString() + ".loc", playerChest.getChests());
                    chests.saveFileConfiguration();
                    autoSell.getMessages().sendMessage(event.getPlayer(), "chestBroken");
                }

            }
        }

    }

}
