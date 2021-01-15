package me.pljr.lottery;

import me.pljr.lottery.commands.ALotteryCommand;
import me.pljr.lottery.commands.LotteryCommand;
import me.pljr.lottery.config.*;
import me.pljr.lottery.listeners.PlayerPreLoginListener;
import me.pljr.lottery.listeners.PlayerQuitListener;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.lottery.managers.PlayerManager;
import me.pljr.lottery.managers.QueryManager;
import me.pljr.pljrapispigot.database.DataSource;
import me.pljr.pljrapispigot.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public final class Lottery extends JavaPlugin {
    private static Lottery instance;
    private static PlayerManager playerManager;
    private static ConfigManager configManager;
    private static GameLotteryManager gameLotteryManager;
    private static QueryManager queryManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        setupConfig();
        setupManagers();
        setupPAPI();
        setupDatabase();
        setupListeners();
        setupCommands();
    }

    private void setupConfig(){
        saveDefaultConfig();
        configManager = new ConfigManager(this, "config.yml");
        MenuItemType.load(new ConfigManager(this, "menus.yml"));
        CfgSettings.load(configManager);
        Lang.load(new ConfigManager(this, "lang.yml"));
        ActionBarType.load(new ConfigManager(this, "actionbars.yml"));
        TitleType.load(new ConfigManager(this, "titles.yml"));
    }

    private void setupManagers(){
        playerManager = new PlayerManager();
        gameLotteryManager = new GameLotteryManager();
        if (CfgSettings.START_ON_STARTUP){
            gameLotteryManager.start();
        }
    }

    private void setupPAPI(){
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PapiExpansion(this).register();
        }
    }

    private void setupDatabase(){
        DataSource dataSource = DataSource.getFromConfig(configManager);
        queryManager = new QueryManager(dataSource);
        queryManager.setupTables();
        for (Player player : Bukkit.getOnlinePlayers()){
            queryManager.loadPlayer(player.getUniqueId());
        }
    }

    private void setupListeners(){
        getServer().getPluginManager().registerEvents(new PlayerPreLoginListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    private void setupCommands(){
        new LotteryCommand().registerCommand(this);
        new ALotteryCommand().registerCommand(this);
    }

    public static Lottery getInstance() {
        return instance;
    }
    public static ConfigManager getConfigManager() {
        return configManager;
    }
    public static GameLotteryManager getGameLotteryManager() {
        return gameLotteryManager;
    }
    public static QueryManager getQueryManager() {
        return queryManager;
    }
    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : Bukkit.getOnlinePlayers()){
            queryManager.savePlayerSync(player.getUniqueId());
        }
    }
}
