package de.xnonymous.autosell.chest;

import de.xnonymous.autosell.AutoSell;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class PlayerChestManager {

    private ArrayList<PlayerChest> playerChests = new ArrayList<>();

    public PlayerChestManager() {
        try {
            for (String key : AutoSell.getAutoSell().getChestConfig().getCfg().getKeys(false)) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(key));
                ArrayList<Location> locations = new ArrayList<>();
                try {
                    ArrayList<Location> locations1 = (ArrayList<Location>) AutoSell.getAutoSell().getChestConfig().getCfg().get(key + ".loc");
                    locations = locations1;
                } catch (Exception ignored) {
                }
                PlayerChest playerChest = new PlayerChest(offlinePlayer, locations, AutoSell.getAutoSell().getChestConfig().getCfg().getBoolean(key + ".debug"));
                add(playerChest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PlayerChest find(Location location) {
        try {
            return playerChests.stream().filter(playerChest -> playerChest.getChests().stream().anyMatch(location1 -> location1.equals(location))).collect(Collectors.toList()).get(0);
        } catch (Exception ignored) {
            return null;
        }
    }

    public PlayerChest find(OfflinePlayer player) {
        try {
            return playerChests.stream().filter(playerChest -> playerChest.getOfflinePlayer().getUniqueId().toString().equals(player.getUniqueId().toString())).collect(Collectors.toList()).get(0);
        } catch (Exception ignored) {
            return null;
        }
    }

    public void add(PlayerChest playerChest) {
        playerChests.add(playerChest);
    }

}
