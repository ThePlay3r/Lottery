package me.pljr.lottery;

import me.pljr.lottery.commands.ALotteryCommand;
import me.pljr.lottery.commands.LotteryCommand;
import me.pljr.lottery.config.*;
import me.pljr.lottery.listeners.PlayerPreLoginListener;
import me.pljr.lottery.listeners.PlayerQuitListener;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.lottery.managers.QueryManager;
import me.pljr.lottery.menus.ConfirmMenu;
import me.pljr.lottery.menus.ListMenu;
import me.pljr.lottery.menus.MainMenu;
import me.pljr.lottery.menus.PlayerMenu;
import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.database.DataSource;
import me.pljr.pljrapi.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class Lottery extends JavaPlugin {
    private static Lottery instance;
    private static ConfigManager configManager;
    private static DataSource dataSource;
    private static GameLotteryManager gameLotteryManager;
    private static QueryManager queryManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        if (!setupPLJRApi()) return;
        setupConfig();
        setupDatabase();
        setupLottery();
        setupListeners();
        setupCommands();
    }

    private boolean setupPLJRApi(){
        PLJRApi api = (PLJRApi) Bukkit.getServer().getPluginManager().getPlugin("PLJRApi");
        if (api == null || !Bukkit.getServer().getPluginManager().getPlugin("PLJRApi").isEnabled()){
            Bukkit.getConsoleSender().sendMessage("§cLottery: PLJRApi not found, disabling plugin!");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }else{
            Bukkit.getConsoleSender().sendMessage("§aLottery: Hooked into PLJRApi!");
            return true;
        }
    }

    private void setupConfig(){
        saveDefaultConfig();
        configManager = new ConfigManager(getConfig(), "§cLottery:", "config.yml");
        CfgConfirmMenu.load();
        CfgListMenu.load();
        CfgMainMenu.load();
        CfgPlayerMenu.load();
        CfgSettings.load();
        CfgLang.load();
        CfgBroadcast.load();
    }

    private void setupDatabase(){
        dataSource = PLJRApi.getDataSource();
        queryManager = new QueryManager(dataSource);
        queryManager.setupTables();
    }

    private void setupLottery(){
        gameLotteryManager = new GameLotteryManager();
        if (CfgSettings.startOnStartup){
            gameLotteryManager.start();
        }
    }

    private void setupListeners(){
        getServer().getPluginManager().registerEvents(new ConfirmMenu(), this);
        getServer().getPluginManager().registerEvents(new ListMenu(), this);
        getServer().getPluginManager().registerEvents(new MainMenu(), this);
        getServer().getPluginManager().registerEvents(new PlayerMenu(), this);
        getServer().getPluginManager().registerEvents(new PlayerPreLoginListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    private void setupCommands(){
        getCommand("lottery").setExecutor(new LotteryCommand());
        getCommand("alottery").setExecutor(new ALotteryCommand());
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

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}