package de.xnonymous.autosell.listener;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.chest.PlayerChest;
import de.xnonymous.autosell.config.impl.ChestConfig;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;

public class PlayerBlockPlaceListener implements Listener {

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
//        event.getPlayer().getInventory().addItem(new ItemBuilder(Material.CHEST).setName("§aAutoSell Chest").toItemStack());
        if (event.canBuild()) {
            if (!event.isCancelled()) {
                if (event.getBlock().getType() == Material.CHEST) {
                    if (event.getItemInHand().getItemMeta() != null) {
                        if (event.getItemInHand().getItemMeta().hasDisplayName()) {
                            if (event.getItemInHand().getItemMeta().getDisplayName().equals("§aAutoSell Chest")) {
                                PlayerChest playerChest = AutoSell.getAutoSell().getPlayerChestRegistry().find(event.getPlayer());
                                if (playerChest == null) {
                                    playerChest = new PlayerChest(event.getPlayer(), new ArrayList<>(), false);
                                    AutoSell.getAutoSell().getPlayerChestRegistry().add(playerChest);
                                }
                                if (playerChest.available() > 0) {
                                    playerChest.add(event.getBlock().getLocation());
                                    event.getPlayer().sendMessage(AutoSell.getAutoSell().getPrefix() + "AutoSell chest was placed.");

                                    String a = event.getPlayer().getUniqueId().toString();
                                    ChestConfig chestConfig = AutoSell.getAutoSell().getChestConfig();
                                    chestConfig.getCfg().set(a + ".loc", playerChest.getChests());
                                    chestConfig.getCfg().set(a + ".debug", playerChest.isDebug());
                                    chestConfig.save();
                                } else {
                                    event.getPlayer().sendMessage(AutoSell.getAutoSell().getPrefix() + "You reached the limit.");
                                    event.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
