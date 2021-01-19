package de.xnonymous.autosell;

import de.xnonymous.autosell.chest.PlayerChestRegistry;
import de.xnonymous.autosell.commands.EnableAutoSellChestDebugCommand;
import de.xnonymous.autosell.commands.GiveAutoSellChestCommand;
import de.xnonymous.autosell.commands.ListAutoSellCommand;
import de.xnonymous.autosell.commands.ReloadConfigCommand;
import de.xnonymous.autosell.config.ConfigRegistry;
import de.xnonymous.autosell.config.impl.ChestConfig;
import de.xnonymous.autosell.config.impl.DefaultConfig;
import de.xnonymous.autosell.config.impl.WorthConfig;
import de.xnonymous.autosell.listener.InventoryClickListener;
import de.xnonymous.autosell.listener.InventoryMoveItemListener;
import de.xnonymous.autosell.listener.PlayerBlockBreakListener;
import de.xnonymous.autosell.listener.PlayerBlockPlaceListener;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public class AutoSell extends JavaPlugin {

    @Getter
    private static AutoSell autoSell;

    private PlayerChestRegistry playerChestRegistry;
    private ConfigRegistry configRegistry;

    private String prefix;
    private double multiplier;
    private int chestLimit;
    private double price;

    private Economy econ;

    private DefaultConfig defaultConfig;
    private ChestConfig chestConfig;
    private WorthConfig worthConfig;

    @Override
    public void onEnable() {
        autoSell = this;
        this.configRegistry = new ConfigRegistry();

        setupEconomy();
        registerConfigs();
        registerListener();
        registerCommands();

        this.playerChestRegistry = new PlayerChestRegistry();
    }

    @Override
    public void onDisable() {

    }

    private void registerConfigs() {
        this.configRegistry.register(defaultConfig = new DefaultConfig());
        this.configRegistry.register(chestConfig = new ChestConfig());
        this.configRegistry.register(worthConfig = new WorthConfig());

        defaultConfig.getCfg().addDefault("Prefix", "&b[AutoSell] &a»");
        defaultConfig.getCfg().addDefault("Multiplier", 1.0);
        defaultConfig.getCfg().addDefault("ChestLimit", 3);
        defaultConfig.getCfg().addDefault("Price", 100.0);
        defaultConfig.getCfg().options().copyDefaults(true);
        defaultConfig.save();

        for (Material value : Material.values()) {
            worthConfig.getCfg().addDefault(value.name(), -1.0);
        }
        worthConfig.getCfg().options().copyDefaults(true);
        worthConfig.save();


        prefix = defaultConfig.getCfg().getString("Prefix").replaceAll("&", "§") + " ";
        multiplier = defaultConfig.getCfg().getDouble("Multiplier");
        chestLimit = defaultConfig.getCfg().getInt("ChestLimit");
        price = defaultConfig.getCfg().getDouble("Price");
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryMoveItemListener(), this);
        pluginManager.registerEvents(new PlayerBlockPlaceListener(), this);
        pluginManager.registerEvents(new PlayerBlockBreakListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
    }

    private void registerCommands() {
        getCommand("GiveAutoSellChest").setExecutor(new GiveAutoSellChestCommand());
        getCommand("EnableAutoSellChestDebug").setExecutor(new EnableAutoSellChestDebugCommand());
        getCommand("ListAutoSellChests").setExecutor(new ListAutoSellCommand());
        getCommand("ReloadAutoSellConfig").setExecutor(new ReloadConfigCommand());
    }

    public void reload() {
        registerConfigs();
    }

}
