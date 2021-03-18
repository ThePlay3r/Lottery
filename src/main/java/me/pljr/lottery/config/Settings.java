package me.pljr.lottery.config;

import lombok.Getter;
import me.pljr.pljrapispigot.managers.ConfigManager;

@Getter
public class Settings {
    private static final String PATH = "settings";

    private final boolean bungee;
    private final boolean multipleTickets;
    private final boolean confirmation;
    private final int cost;
    private final boolean broadcastChat;
    private final boolean broadcastActionbar;
    private final boolean broadcastTitle;
    private final boolean startOnStartup;
    private final int countdown;
    private final boolean restartOnEnd;
    private final boolean cachePlayers;

    public Settings(ConfigManager config){
        bungee = config.getBoolean(PATH+".bungee");
        multipleTickets = config.getBoolean(PATH+".multiple-tickets");
        confirmation = config.getBoolean(PATH+".confirmation");
        cost = config.getInt(PATH+".cost");
        broadcastChat = config.getBoolean(PATH+".broadcast-chat");
        broadcastActionbar = config.getBoolean(PATH+".broadcast-actionbar");
        broadcastTitle = config.getBoolean(PATH+".broadcast-title");
        startOnStartup = config.getBoolean(PATH+".start-on-startup");
        countdown = config.getInt(PATH+".countdown");
        restartOnEnd = config.getBoolean(PATH+".restart-on-end");
        cachePlayers = config.getBoolean(PATH+".cache-players");
    }
}
