package de.xnonymous.autosell;

import de.xnonymous.autosell.chest.PlayerChestRegistry;
import de.xnonymous.autosell.commands.EnableAutoSellChestDebugCommand;
import de.xnonymous.autosell.commands.GiveAutoSellChestCommand;
import de.xnonymous.autosell.commands.ListAutoSellCommand;
import de.xnonymous.autosell.config.ConfigRegistry;
import de.xnonymous.autosell.config.impl.ChestConfig;
import de.xnonymous.autosell.config.impl.DefaultConfig;
import de.xnonymous.autosell.listener.InventoryClickListener;
import de.xnonymous.autosell.listener.InventoryMoveItemListener;
import de.xnonymous.autosell.listener.PlayerBlockBreakListener;
import de.xnonymous.autosell.listener.PlayerBlockPlaceListener;
import lombok.Getter;
import lombok.Setter;
import net.ess3.api.IEssentials;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
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

    private IEssentials essentials;
    private Economy econ;

    private DefaultConfig defaultConfig;
    private ChestConfig chestConfig;

    @Override
    public void onEnable() {
        autoSell = this;
        this.configRegistry = new ConfigRegistry();
        this.essentials = (IEssentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

        setupEconomy();
        registerConfigs();
        registerListener();
        registerCommands();

        prefix = defaultConfig.getCfg().getString("Prefix").replaceAll("&", "§") + " ";
        multiplier = defaultConfig.getCfg().getDouble("Multiplier");
        chestLimit = defaultConfig.getCfg().getInt("ChestLimit");
        this.playerChestRegistry = new PlayerChestRegistry();
    }

    @Override
    public void onDisable() {

    }

    private void registerConfigs() {
        this.configRegistry.register(defaultConfig = new DefaultConfig());
        this.configRegistry.register(chestConfig = new ChestConfig());

        defaultConfig.getCfg().addDefault("Prefix", "&b[AutoSell] &a>");
        defaultConfig.getCfg().addDefault("Multiplier", 1.0);
        defaultConfig.getCfg().addDefault("ChestLimit", 3);
        defaultConfig.getCfg().options().copyDefaults(true);
        defaultConfig.save();


        try {
            ItemStack itemStack = essentials.getItemDb().get("cobblestone");
            System.out.println(essentials.getWorth().getPrice(essentials, itemStack));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    }

}
