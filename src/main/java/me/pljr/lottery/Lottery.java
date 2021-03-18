package me.pljr.lottery;

import lombok.Getter;
import me.pljr.lottery.commands.ALotteryCommand;
import me.pljr.lottery.commands.LotteryCommand;
import me.pljr.lottery.config.*;
import me.pljr.lottery.listeners.AsyncPlayerPreLoginListener;
import me.pljr.lottery.listeners.PlayerQuitListener;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.lottery.managers.PlayerManager;
import me.pljr.lottery.managers.QueryManager;
import me.pljr.pljrapispigot.PLJRApiSpigot;
import me.pljr.pljrapispigot.database.DataSource;
import me.pljr.pljrapispigot.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public final class Lottery extends JavaPlugin {

    private static Lottery instance;
    public static Logger log;
    private PLJRApiSpigot pljrApiSpigot;

    private ConfigManager configManager;
    @Getter private Settings settings;

    private QueryManager queryManager;
    private PlayerManager playerManager;
    private GameLotteryManager gameLotteryManager;

    public static Lottery get(){
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        log = getLogger();
        instance = this;
        if (!setupPLJRApi()) return;
        setupConfig();
        setupDatabase();
        setupManagers();
        setupPAPI();
        setupListeners();
        setupCommands();
    }

    public boolean setupPLJRApi(){
        if (PLJRApiSpigot.get() == null){
            getLogger().warning("PLJRApi-Spigot is not enabled!");
            return false;
        }
        pljrApiSpigot = PLJRApiSpigot.get();
        return true;
    }


    private void setupConfig(){
        saveDefaultConfig();
        configManager = new ConfigManager(this, "config.yml");
        MenuItemType.load(new ConfigManager(this, "menus.yml"));
        settings = new Settings(configManager);
        Lang.load(new ConfigManager(this, "lang.yml"));
        ActionBarType.load(new ConfigManager(this, "actionbars.yml"));
        TitleType.load(new ConfigManager(this, "titles.yml"));
    }

    private void setupManagers(){
        playerManager = new PlayerManager(this, queryManager, settings.isCachePlayers());
        gameLotteryManager = new GameLotteryManager(settings, playerManager);
        if (settings.isStartOnStartup()){
            gameLotteryManager.start();
        }
    }

    private void setupPAPI(){
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PapiExpansion(this, playerManager, gameLotteryManager).register();
        }
    }

    private void setupDatabase(){
        DataSource dataSource = pljrApiSpigot.getDataSource(configManager);
        dataSource.initPool("Lottery-Pool");
        queryManager = new QueryManager(dataSource);
        queryManager.setupTables();
        for (Player player : Bukkit.getOnlinePlayers()){
            queryManager.loadPlayer(player.getUniqueId());
        }
    }

    private void setupListeners(){
        getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(playerManager), this);
    }

    private void setupCommands(){
        new LotteryCommand(settings, playerManager, gameLotteryManager).registerCommand(this);
        new ALotteryCommand(gameLotteryManager).registerCommand(this);
    }

    @Override
    public void onDisable() {
        instance = null;
        for (Player player : Bukkit.getOnlinePlayers()){
            playerManager.getPlayer(player.getUniqueId(), queryManager::savePlayer);
        }
    }
}
