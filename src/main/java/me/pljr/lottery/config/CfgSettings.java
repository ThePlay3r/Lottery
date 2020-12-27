package me.pljr.lottery.config;

import me.pljr.pljrapispigot.managers.ConfigManager;

public class CfgSettings {
    public static boolean BUNGEE;
    public static boolean MULTIPLE_TICKETS;
    public static boolean CONFIRMATION;
    public static int COST;
    public static boolean BROADCAST_CHAT;
    public static boolean BROADCAST_ACTIONBAR;
    public static boolean BROADCAST_TITLE;
    public static boolean START_ON_STARTUP;
    public static int COUNTDOWN;
    public static boolean RESTART_ON_END;

    public static void load(ConfigManager config){
        CfgSettings.BUNGEE = config.getBoolean("settings.bungee");
        CfgSettings.MULTIPLE_TICKETS = config.getBoolean("settings.multiple-tickets");
        CfgSettings.CONFIRMATION = config.getBoolean("settings.confirmation");
        CfgSettings.COST = config.getInt("settings.cost");
        CfgSettings.BROADCAST_CHAT = config.getBoolean("settings.broadcast-chat");
        CfgSettings.BROADCAST_ACTIONBAR = config.getBoolean("settings.broadcast-actionbar");
        CfgSettings.BROADCAST_TITLE = config.getBoolean("settings.broadcast-title");
        CfgSettings.START_ON_STARTUP = config.getBoolean("settings.start-on-startup");
        CfgSettings.COUNTDOWN = config.getInt("settings.countdown");
        CfgSettings.RESTART_ON_END = config.getBoolean("settings.restart-on-end");
    }
}
