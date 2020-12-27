package me.pljr.lottery.config;

import me.pljr.pljrapispigot.managers.ConfigManager;

import java.util.HashMap;
import java.util.List;

public enum Lang {
    PLAYER_WIN,
    BUY_SUCCESS,
    BUY_FAILURE,
    RESTART_SUCCESS,
    ADD_MONEY_SUCCESS;
    public static List<String> HELP;
    public static List<String> ADMIN_HELP;

    private static HashMap<Lang, String> lang;

    public static void load(ConfigManager config){
        HELP = config.getStringList("help");
        ADMIN_HELP = config.getStringList("admin-help");
        lang = new HashMap<>();
        for (Lang lang : values()){
            Lang.lang.put(lang, config.getString("lang."+lang));
        }
    }

    public String get(){
        return lang.get(this);
    }
}
