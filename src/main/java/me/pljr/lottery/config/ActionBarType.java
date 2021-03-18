package me.pljr.lottery.config;

import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.objects.PLJRActionBar;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public enum ActionBarType {
    BROADCAST_START(new PLJRActionBar("&a&lLOTTERY: &fNew &elottery &fjust started! &b/lottery", 40)),
    BROADCAST_END(new PLJRActionBar("&a&lLOTTERY: &e{winner} &fwon &e{winAmount}&f!", 40)),
    BROADCAST_NO_WINNER(new PLJRActionBar("&a&lLOTTERY: &eNoone participated.", 40));

    private static HashMap<ActionBarType, PLJRActionBar> actionbars;
    private final PLJRActionBar defaultValue;

    ActionBarType(PLJRActionBar defaultValue){
        this.defaultValue = defaultValue;
    }

    public static void load(ConfigManager config){
        actionbars = new HashMap<>();
        FileConfiguration fileConfig = config.getConfig();
        for (ActionBarType actionBarType : values()){
            if (!fileConfig.isSet(actionBarType.toString())){
                config.setPLJRActionBar(actionBarType.toString(), actionBarType.defaultValue);
            }else{
                actionbars.put(actionBarType, config.getPLJRActionBar(actionBarType.toString()));
            }
        }
        config.save();
    }

    public PLJRActionBar get(){
        return actionbars.getOrDefault(this, defaultValue);
    }
}
