package me.pljr.lottery.config;

import me.pljr.lottery.Lottery;
import me.pljr.pljrapi.managers.ConfigManager;

public class CfgSettings {
    private final static ConfigManager config = Lottery.getConfigManager();

    public static boolean bungee;
    public static boolean multipleTickets;
    public static boolean confirmation;
    public static int cost;
    public static boolean broadcastChat;
    public static boolean broadcastActionBar;
    public static boolean broadcastTitle;
    public static boolean startOnStartup;
    public static int countdown;
    public static boolean restartOnEnd;

    public static void load(){
        CfgSettings.bungee = config.getBoolean("settings.bungee");
        CfgSettings.multipleTickets = config.getBoolean("settings.multiple-tickets");
        CfgSettings.confirmation = config.getBoolean("settings.confirmation");
        CfgSettings.cost = config.getInt("settings.cost");
        CfgSettings.broadcastChat = config.getBoolean("settings.broadcast-chat");
        CfgSettings.broadcastActionBar = config.getBoolean("settings.broadcast-actionbar");
        CfgSettings.broadcastTitle = config.getBoolean("settings.broadcast-title");
        CfgSettings.startOnStartup = config.getBoolean("settings.start-on-startup");
        CfgSettings.countdown = config.getInt("settings.countdown");
        CfgSettings.restartOnEnd = config.getBoolean("settings.restart-on-end");
    }
}
