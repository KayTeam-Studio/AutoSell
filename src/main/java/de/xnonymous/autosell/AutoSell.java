package de.xnonymous.autosell;

import de.xnonymous.autosell.chest.PlayerChestRegistry;
import de.xnonymous.autosell.commands.*;
import de.xnonymous.autosell.config.ConfigRegistry;
import de.xnonymous.autosell.listener.InventoryClickListener;
import de.xnonymous.autosell.listener.InventoryMoveItemListener;
import de.xnonymous.autosell.listener.PlayerBlockBreakListener;
import de.xnonymous.autosell.listener.PlayerBlockPlaceListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.kayteamapi.yaml.Yaml;

public class AutoSell extends JavaPlugin {

    private final Yaml settings = new Yaml(this, "settings");
    private final Yaml messages = new Yaml(this, "messages");
    private final Yaml worth = new Yaml(this, "worth");
    private final Yaml chests = new Yaml(this, "chests");

    private PlayerChestRegistry playerChestRegistry;
    private ConfigRegistry configRegistry;
    private double multiplier;
    private int chestLimit;
    private double price;

    private Economy economy;

    @Override
    public void onEnable() {
        this.configRegistry = new ConfigRegistry();

        setupEconomy();
        registerFiles();
        registerListener();
        registerCommands();

        this.playerChestRegistry = new PlayerChestRegistry(this);
    }

    @Override
    public void onDisable() {

    }

    public void onReload() {
        settings.reloadFileConfiguration();
        messages.reloadFileConfiguration();
        worth.registerFileConfiguration();
        for (Material value : Material.values()) {
            if (!worth.contains(value.name())) {
                worth.set(value.name(), 0.0);
            }
        }
        worth.saveFileConfiguration();
        chests.reloadFileConfiguration();
    }

    private void registerFiles() {
        settings.registerFileConfiguration();
        multiplier = settings.getDouble("Multiplier");
        chestLimit = settings.getInt("ChestLimit");
        price = settings.getDouble("Price");

        messages.registerFileConfiguration();
        worth.registerFileConfiguration();
        for (Material value : Material.values()) {
            if (!worth.contains(value.name())) {
                worth.set(value.name(), 0.0);
            }
        }
        worth.saveFileConfiguration();
        chests.registerFileConfiguration();
    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryMoveItemListener(this), this);
        pluginManager.registerEvents(new PlayerBlockPlaceListener(this), this);
        pluginManager.registerEvents(new PlayerBlockBreakListener(this), this);
        pluginManager.registerEvents(new InventoryClickListener(this), this);
    }

    private void registerCommands() {
        new BuyAutoSellChestCommand(this).registerCommand(this);
        new GiveAutoSellChestCommand(this).registerCommand(this);
        new EnableAutoSellChestDebugCommand(this).registerCommand(this);
        new ListAutoSellCommand(this).registerCommand(this);
        new ReloadConfigCommand(this).registerCommand(this);
    }

    public Yaml getSettings() {
        return settings;
    }

    public Yaml getMessages() {
        return messages;
    }

    public Yaml getWorth() {
        return worth;
    }

    public PlayerChestRegistry getPlayerChestRegistry() {
        return playerChestRegistry;
    }

    public Yaml getChests() {
        return chests;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public int getChestLimit() {
        return chestLimit;
    }

    public double getPrice() {
        return price;
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> registeredServiceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (registeredServiceProvider == null) {
            return;
        }
        economy = registeredServiceProvider.getProvider();
    }

    public Economy getEconomy() {
        return economy;
    }

}
