package de.xnonymous.autosell.listener;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.chest.PlayerChest;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.ArrayList;

public class PlayerBlockPlaceListener implements Listener {

    private final AutoSell AUTOSELL;

    public PlayerBlockPlaceListener(AutoSell AUTOSELL) {
        this.AUTOSELL = AUTOSELL;
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
        Yaml messages = AUTOSELL.getMessages();
        if (event.canBuild()) {
            if (!event.isCancelled()) {
                if (event.getBlock().getType() == Material.CHEST) {
                    if (event.getItemInHand().getItemMeta() != null) {
                        if (event.getItemInHand().getItemMeta().hasDisplayName()) {
                            if (event.getItemInHand().getItemMeta().getDisplayName().equals("§aAutoSell Chest")) {
                                PlayerChest playerChest = AUTOSELL.getPlayerChestRegistry().find(event.getPlayer());
                                if (playerChest == null) {
                                    playerChest = new PlayerChest(AUTOSELL, event.getPlayer(), new ArrayList<>(), false);
                                    AUTOSELL.getPlayerChestRegistry().add(playerChest);
                                }
                                if (playerChest.available() > 0) {
                                    playerChest.add(event.getBlock().getLocation());
                                    String uuid = event.getPlayer().getUniqueId().toString();
                                    Yaml chests = AUTOSELL.getChests();
                                    chests.set(uuid + ".loc", playerChest.getChests());
                                    chests.set(uuid + ".debug", playerChest.isDebug());
                                    chests.saveFileConfiguration();
                                    messages.sendMessage(event.getPlayer(), "chestPlaced");
                                } else {
                                    messages.sendMessage(event.getPlayer(), "chestLimit");
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
