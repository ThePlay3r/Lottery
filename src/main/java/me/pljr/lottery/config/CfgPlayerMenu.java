package me.pljr.lottery.config;

import me.pljr.lottery.Lottery;
import me.pljr.pljrapi.managers.ConfigManager;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CfgPlayerMenu {
    private final static ConfigManager config = Lottery.getConfigManager();

    public static String title;
    public static String playerHeadName;
    public static List<String> playerHeadLore;
    public static ItemStack background1;
    public static ItemStack background2;
    public static ItemStack totalEarnings;
    public static ItemStack ticketsBought;
    public static ItemStack back;
    public static ItemStack biggestWin;

    public static void load(){
        CfgPlayerMenu.title = config.getString("player-menu.title");
        CfgPlayerMenu.playerHeadName = config.getString("player-menu.player-head.name");
        CfgPlayerMenu.playerHeadLore = config.getStringList("player-menu.player-head.lore");
        CfgPlayerMenu.background1 = config.getSimpleItemStack("player-menu.background-1");
        CfgPlayerMenu.background2 = config.getSimpleItemStack("player-menu.background-2");
        CfgPlayerMenu.totalEarnings = config.getSimpleItemStack("player-menu.total-earnings");
        CfgPlayerMenu.ticketsBought = config.getSimpleItemStack("player-menu.tickets-bought");
        CfgPlayerMenu.back = config.getSimpleItemStack("player-menu.back");
        CfgPlayerMenu.biggestWin = config.getSimpleItemStack("player-menu.biggest-win");
    }
}
