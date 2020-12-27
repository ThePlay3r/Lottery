package me.pljr.lottery.config;

import me.pljr.pljrapispigot.managers.ConfigManager;
import org.bukkit.inventory.ItemStack;

public class CfgPlayerMenu {
    public static String TITLE;
    public static ItemStack PLAYER_HEAD;
    public static ItemStack BACKGROUND_1;
    public static ItemStack BACKGROUND_2;
    public static ItemStack TOTAL_EARNINGS;
    public static ItemStack TICKETS_BOUGHT;
    public static ItemStack BACK;
    public static ItemStack BIGGEST_WIN;

    public static void load(ConfigManager config){
        CfgPlayerMenu.TITLE = config.getString("player-menu.title");
        CfgPlayerMenu.PLAYER_HEAD = config.getHead("player-menu.player-head");
        CfgPlayerMenu.BACKGROUND_1 = config.getSimpleItemStack("player-menu.background-1");
        CfgPlayerMenu.BACKGROUND_2 = config.getSimpleItemStack("player-menu.background-2");
        CfgPlayerMenu.TOTAL_EARNINGS = config.getSimpleItemStack("player-menu.total-earnings");
        CfgPlayerMenu.TICKETS_BOUGHT = config.getSimpleItemStack("player-menu.tickets-bought");
        CfgPlayerMenu.BACK = config.getSimpleItemStack("player-menu.back");
        CfgPlayerMenu.BIGGEST_WIN = config.getSimpleItemStack("player-menu.biggest-win");
    }
}
