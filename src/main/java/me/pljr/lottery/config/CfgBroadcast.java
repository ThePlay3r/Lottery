package me.pljr.lottery.config;

import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.objects.PLJRActionBar;
import me.pljr.pljrapispigot.objects.PLJRTitle;

import java.util.List;

public class CfgBroadcast {
    public static List<String> CHAT_START;
    public static List<String> CHAT_END;
    public static List<String> CHAT_NO_WINNER;
    public static PLJRActionBar ACTIONBAR_START;
    public static PLJRActionBar ACTIONBAR_END;
    public static PLJRActionBar ACTIONBAR_NO_WINNER;
    public static PLJRTitle TITLE_START;
    public static PLJRTitle TITLE_END;
    public static PLJRTitle TITLE_NO_WINNER;

    public static void load(ConfigManager config){
        CfgBroadcast.CHAT_START = config.getStringList("broadcast.chat.start");
        CfgBroadcast.CHAT_END = config.getStringList("broadcast.chat.end");
        CfgBroadcast.CHAT_NO_WINNER = config.getStringList("broadcast.chat.no-winner");
        CfgBroadcast.ACTIONBAR_START = config.getPLJRActionBar("broadcast.actionbar.start");
        CfgBroadcast.ACTIONBAR_END = config.getPLJRActionBar("broadcast.actionbar.end");
        CfgBroadcast.ACTIONBAR_NO_WINNER = config.getPLJRActionBar("broadcast.actionbar.no-winner");
        CfgBroadcast.TITLE_START = config.getPLJRTitle("broadcast.title.start");
        CfgBroadcast.TITLE_END = config.getPLJRTitle("broadcast.title.end");
        CfgBroadcast.TITLE_NO_WINNER = config.getPLJRTitle("broadcast.title.no-winner");
    }
}
