package de.xnonymous.autosell.config;

import de.xnonymous.autosell.AutoSell;
import de.xnonymous.autosell.structure.INameable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class Config implements INameable {

    private final AutoSell autoSell;

    private String name;
    private File file;
    private FileConfiguration cfg;

    public Config(AutoSell autoSell, String name) {
        this.autoSell = autoSell;
        this.name = name;
        this.file = new File(autoSell.getDataFolder(), name + ".yml");
        this.cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        this.file = new File(autoSell.getDataFolder(), name + ".yml");
        this.cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
