package me.pljr.lottery.config;

import me.pljr.lottery.Lottery;
import me.pljr.pljrapi.managers.ConfigManager;
import me.pljr.pljrapi.objects.PLJRActionBar;
import me.pljr.pljrapi.objects.PLJRTitle;

import java.util.List;

public class CfgBroadcast {
    private final static ConfigManager config = Lottery.getConfigManager();

    public static List<String> chatStart;
    public static List<String> chatEnd;
    public static List<String> chatNoWinner;
    public static PLJRActionBar actionBarStart;
    public static PLJRActionBar actionBarEnd;
    public static PLJRActionBar actionBarNoWinner;
    public static PLJRTitle titleStart;
    public static PLJRTitle titleEnd;
    public static PLJRTitle titleNoWinner;

    public static void load(){
        CfgBroadcast.chatStart = config.getStringList("broadcast.chat.start");
        CfgBroadcast.chatEnd = config.getStringList("broadcast.chat.end");
        CfgBroadcast.chatNoWinner = config.getStringList("broadcast.chat.no-winner");
        CfgBroadcast.actionBarStart = config.getPLJRActionBar("broadcast.actionbar.start");
        CfgBroadcast.actionBarEnd = config.getPLJRActionBar("broadcast.actionbar.end");
        CfgBroadcast.actionBarNoWinner = config.getPLJRActionBar("broadcast.actionbar.no-winner");
        CfgBroadcast.titleStart = config.getPLJRTitle("broadcast.title.start");
        CfgBroadcast.titleEnd = config.getPLJRTitle("broadcast.title.end");
        CfgBroadcast.titleNoWinner = config.getPLJRTitle("broadcast.title.no-winner");
    }
}
