package de.xnonymous.autosell.listener;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.chest.PlayerChest;
import de.xnonymous.autosell.config.impl.ChestConfig;
import de.xnonymous.autosell.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class PlayerBlockBreakListener implements Listener {

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            if (event.getBlock().getType() == Material.CHEST) {
                Location location = event.getBlock().getLocation();
                PlayerChest playerChest = AutoSell.getAutoSell().getPlayerChestManager().find(location);

                if (playerChest != null) {
                    playerChest.remove(location);
                    event.setDropItems(false);
                    location.getWorld().dropItem(location, new ItemBuilder(Material.CHEST).setName("§aAutoSell Chest").toItemStack());
                    ChestConfig chestConfig = AutoSell.getAutoSell().getChestConfig();
                    chestConfig.getCfg().set(playerChest.getOfflinePlayer().getUniqueId().toString() + ".loc", playerChest.getChests());
                    chestConfig.save();
                    event.getPlayer().sendMessage(AutoSell.getAutoSell().getPrefix() + "AutoSelling chest broken");
                }

            }
        }

    }

}
