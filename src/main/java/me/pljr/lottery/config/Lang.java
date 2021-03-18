package me.pljr.lottery.config;

import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.utils.FormatUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public enum Lang {
    HELP("" +
            "\n&a&lLottery Help" +
            "\n" +
            "\n&e/lottery &8» &fOpens the main GUI." +
            "\n&e/lottery help &8» &fDisplays this message." +
            "\n&e/lottery buy <amount> &8» &fBuys tickets, if you have enough money." +
            "\n&e/lottery list &8» &fOpens GUI with current players in lottery." +
            "\n&e/lottery stats <player> &8» &fDisplays player's statistics."),

    ADMIN_HELP("" +
            "\n&a&lLottery Admin-Help" +
            "\n" +
            "\n&e/alottery restart &8» &fStops current and starts a brand new lottery." +
            "\n&e/alottery help &8» &fDisplays this message." +
            "\n&e/alottery draw &8» &fEnds the lottery and selects winner." +
            "\n&e/alottery add <amount> &8» &fAdds money to lottery."),

    BROADCAST_START("" +
            "\n&a&lLOTTERY" +
            "\n&fNew &elottery &fjust started! &7(&b{cost} &fper ticket&7)" +
            "\n&8» &fUse &b/lottery &ffor more information!" +
            "\n"),

    BROADCAST_END("" +
            "\n&a&lLOTTERY" +
            "\n&eLottery &fjust ended!" +
            "\n&8» &fWinner: &e{winner}" +
            "\n&8» &fAmount: &e{winAmount}" +
            "\n"),

    BROADCAST_NO_WINNER("" +
            "\n&a&lLOTTERY" +
            "\n&eLottery &fjust ended!" +
            "\n&8» &fNoone participated." +
            "\n"),

    MENU_TITLE("&8&lLottery"),
    PLAYER_WIN("&aLottery &8» &fYou won &e{winAmount} &ffrom lottery!"),
    BUY_SUCCESS("&aLottery &8» &fYou have successfully bought &b{amount} &fticket(s)!"),
    BUY_FAILURE("&aLottery &8» &fYou can't buy &b{amount} &fticket(s)!"),
    RESTART_SUCCESS("&aLottery &8» &bSuccessfully &frestarted lottery."),
    ADD_MONEY_SUCCESS("&aLottery &8» &bSuccessfully &fadded &b{amount}$ &fto lottery.");

    private static HashMap<Lang, String> lang;
    private final String defaultValue;

    Lang(String defaultValue){
        this.defaultValue = defaultValue;
    }

    public static void load(ConfigManager config){
        lang = new HashMap<>();
        FileConfiguration fileConfig = config.getConfig();
        for (Lang lang : values()){
            if (!fileConfig.isSet(lang.toString())){
                fileConfig.set(lang.toString(), lang.defaultValue);
            }else{
                Lang.lang.put(lang, config.getString(lang.toString()));
            }
        }
        config.save();
    }

    public String get(){
        return lang.getOrDefault(this, FormatUtil.colorString(defaultValue));
    }
}
