package me.pljr.lottery.config;

import me.pljr.pljrapispigot.managers.ConfigManager;
import org.bukkit.inventory.ItemStack;

public class CfgMainMenu {
    public static String TITLE;
    public static ItemStack PLAYER_HEAD;
    public static ItemStack BACKGROUND_1;
    public static ItemStack BACKGROUND_2;
    public static ItemStack CURRENT_MONEY;
    public static ItemStack INSTRUCTIONS;
    public static ItemStack BUY;
    public static ItemStack LIST;
    public static ItemStack HELP;
    public static ItemStack TIME_LEFT;
    public static ItemStack ADMIN;

    public static void load(ConfigManager config){
        CfgMainMenu.TITLE = config.getString("main-menu.title");
        CfgMainMenu.PLAYER_HEAD = config.getHead("main-menu.player-head");
        CfgMainMenu.BACKGROUND_1 = config.getSimpleItemStack("main-menu.background-1");
        CfgMainMenu.BACKGROUND_2 = config.getSimpleItemStack("main-menu.background-2");
        CfgMainMenu.CURRENT_MONEY = config.getSimpleItemStack("main-menu.current-money");
        CfgMainMenu.INSTRUCTIONS = config.getSimpleItemStack("main-menu.instructions");
        CfgMainMenu.BUY = config.getSimpleItemStack("main-menu.buy");
        CfgMainMenu.LIST = config.getSimpleItemStack("main-menu.list");
        CfgMainMenu.HELP = config.getSimpleItemStack("main-menu.help");
        CfgMainMenu.TIME_LEFT = config.getSimpleItemStack("main-menu.time-left");
        CfgMainMenu.ADMIN = config.getSimpleItemStack("main-menu.admin");
    }
}
