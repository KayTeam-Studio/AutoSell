package de.xnonymous.autosell.chest;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.config.impl.WorthConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.ArrayList;

@AllArgsConstructor
@Data
public class PlayerChest {

    private OfflinePlayer offlinePlayer;
    private ArrayList<Location> chests;
    private boolean debug;

    public void handle(Location location) {
        Chest chest = (Chest) location.getBlock().getState();
        for (ItemStack content : chest.getBlockInventory().getContents()) {
            if (content == null)
                continue;

            try {
                WorthConfig worthConfig = AutoSell.getAutoSell().getWorthConfig();
                double price =  worthConfig.getCfg().getDouble(content.getType().name());

                double real = (price * content.getAmount()) * AutoSell.getAutoSell().getMultiplier();

                if (1 > real) {
                    debug("Price is not defined in worth.yml!");
                    debug("Please set a price for " + content.getType().name());
                    continue;
                }

                debug("Selling " + content.getAmount() + " " + content.getType().name().replaceAll("_", "") + " for " + round(real, 2) + "$");
                content.setAmount(0);
                AutoSell.getAutoSell().getEcon().depositPlayer(offlinePlayer, real);
            } catch (Exception ignored) {
                try {
                    debug("Could not sell " + content.getType().name());
                } catch (Exception e) {
                    debug("Item error!");
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

    public void debug(String msg) {
        if (offlinePlayer.isOnline() && debug)
            offlinePlayer.getPlayer().sendMessage(AutoSell.getAutoSell().getPrefix() + msg);
    }

    public int available() {
        return AutoSell.getAutoSell().getChestLimit() - chests.size();
    }

    public double round(double zahl, int stellen) {
        return (int)zahl + (Math.round(Math.pow(10,stellen)*(zahl-(int)zahl)))/(Math.pow(10,stellen));
    }

}
