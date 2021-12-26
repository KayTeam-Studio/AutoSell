package de.xnonymous.autosell.chest;

import de.xnonymous.autosell.AutoSell;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.UUID;

@Data
public class PlayerChestRegistry {

    private final AutoSell autoSell;
    private ArrayList<PlayerChest> playerChests = new ArrayList<>();

    public PlayerChestRegistry(AutoSell autoSell) {
        this.autoSell = autoSell;
        try {
            for (String key : autoSell.getChests().getFileConfiguration().getKeys(false)) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(key));
                ArrayList<Location> locations = new ArrayList<>();
                try {
                    ArrayList<Location> locations1 = (ArrayList<Location>) autoSell.getChests().getFileConfiguration().get(key + ".loc");
                    locations = locations1;
                } catch (Exception ignored) {
                }
                PlayerChest playerChest = new PlayerChest(autoSell, offlinePlayer, locations, autoSell.getChests().getBoolean(key + ".debug"));
                add(playerChest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PlayerChest find(Location location) {
        return playerChests.stream().filter(playerChest -> playerChest.getChests().stream().anyMatch(location1 -> location1.equals(location))).findFirst().orElse(null);
    }

    public PlayerChest find(OfflinePlayer player) {
        return playerChests.stream().filter(playerChest -> playerChest.getOfflinePlayer().getUniqueId().toString().equals(player.getUniqueId().toString())).findFirst().orElse(null);
    }

    public void add(PlayerChest playerChest) {
        playerChests.add(playerChest);
    }

}
