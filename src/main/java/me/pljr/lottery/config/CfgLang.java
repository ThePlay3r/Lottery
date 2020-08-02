package me.pljr.lottery.config;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.enums.Lang;
import me.pljr.pljrapi.managers.ConfigManager;

import java.util.HashMap;
import java.util.List;

public class CfgLang {
    private final static ConfigManager config = Lottery.getConfigManager();

    public static List<String> help;
    public static List<String> adminHelp;
    public static HashMap<Lang, String> lang = new HashMap<>();

    public static void load(){
        CfgLang.help = config.getStringList("help");
        CfgLang.adminHelp = config.getStringList("admin-help");
        for (Lang lang : Lang.values()){
            CfgLang.lang.put(lang, config.getString("lang." + lang.toString()));
        }
    }
}
