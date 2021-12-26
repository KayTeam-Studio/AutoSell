package de.xnonymous.autosell.chest;

import de.xnonymous.autosell.AutoSell;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.ArrayList;

@Data
public class PlayerChest {

    private final AutoSell AUTOSELL;
    private OfflinePlayer offlinePlayer;
    private ArrayList<Location> chests;
    private boolean debug;

    public PlayerChest(AutoSell AUTOSELL, OfflinePlayer offlinePlayer, ArrayList<Location> locations, boolean debug) {
        this.AUTOSELL = AUTOSELL;
        this.offlinePlayer = offlinePlayer;
        this.chests = locations;
        this.debug = debug;
    }

    public void handle(Location location) {
        Chest chest = (Chest) location.getBlock().getState();
        for (ItemStack content : chest.getBlockInventory().getContents()) {
            if (content == null)
                continue;
            try {
                Yaml worth = AUTOSELL.getWorth();
                double price =  worth.getDouble(content.getType().name(), 0.0);
                double real = (price * content.getAmount()) * AUTOSELL.getMultiplier();
                if (1 > real) {
                    AUTOSELL.getMessages().sendMessage(offlinePlayer.getPlayer(), "debug.priceUndefined", new String[][]{
                            {"%item%", content.getType().toString()}
                    });
                    continue;
                }
                if (offlinePlayer.isOnline() && debug) {
                    AUTOSELL.getMessages().sendMessage(offlinePlayer.getPlayer(), "debug.selling", new String[][]{
                            {"%item%", content.getType().toString()},
                            {"%amount%", content.getAmount() + ""},
                            {"%money%", round(real, 2) + ""}
                    });
                }
                content.setAmount(0);
                AUTOSELL.getEconomy().depositPlayer(offlinePlayer, real);
            } catch (Exception e) {
                e.printStackTrace();
                if (offlinePlayer.isOnline() && debug) {
                    AUTOSELL.getMessages().sendMessage(offlinePlayer.getPlayer(), "debug.error");
                }
            }
        }
    }

    public void add(Location location) {
        this.chests.add(location);
    }

    public void remove(Location location) {
        this.chests.remove(location);
    }

    public int available() {
        return AUTOSELL.getChestLimit() - chests.size();
    }

    public double round(double zahl, int stellen) {
        return (int)zahl + (Math.round(Math.pow(10,stellen)*(zahl-(int)zahl)))/(Math.pow(10,stellen));
    }

}
