package me.pljr.lottery.config;

import me.pljr.lottery.Lottery;
import me.pljr.pljrapi.managers.ConfigManager;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CfgMainMenu {
    private final static ConfigManager config = Lottery.getConfigManager();

    public static String title;
    public static String playerHeadName;
    public static List<String> playerHeadLore;
    public static ItemStack background1;
    public static ItemStack background2;
    public static ItemStack currentMoney;
    public static ItemStack instructions;
    public static ItemStack buy;
    public static ItemStack list;
    public static ItemStack help;
    public static ItemStack timeLeft;
    public static ItemStack admin;

    public static void load(){
        CfgMainMenu.title = config.getString("main-menu.title");
        CfgMainMenu.playerHeadName = config.getString("main-menu.player-head.name");
        CfgMainMenu.playerHeadLore = config.getStringList("main-menu.player-head.lore");
        CfgMainMenu.background1 = config.getSimpleItemStack("main-menu.background-1");
        CfgMainMenu.background2 = config.getSimpleItemStack("main-menu.background-2");
        CfgMainMenu.currentMoney = config.getSimpleItemStack("main-menu.current-money");
        CfgMainMenu.instructions = config.getSimpleItemStack("main-menu.instructions");
        CfgMainMenu.buy = config.getSimpleItemStack("main-menu.buy");
        CfgMainMenu.list = config.getSimpleItemStack("main-menu.list");
        CfgMainMenu.help = config.getSimpleItemStack("main-menu.help");
        CfgMainMenu.timeLeft = config.getSimpleItemStack("main-menu.time-left");
        CfgMainMenu.admin = config.getSimpleItemStack("main-menu.admin");
    }
}
