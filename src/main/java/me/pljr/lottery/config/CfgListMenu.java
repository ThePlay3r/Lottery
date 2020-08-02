package me.pljr.lottery.config;

import me.pljr.lottery.Lottery;
import me.pljr.pljrapi.managers.ConfigManager;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CfgListMenu {
    private final static ConfigManager config = Lottery.getConfigManager();

    public static String title;
    public static String headsName;
    public static List<String> headsLore;
    public static ItemStack background1;
    public static ItemStack background2;
    public static ItemStack back;

    public static void load(){
        CfgListMenu.title = config.getString("list-menu.title");
        CfgListMenu.headsName = config.getString("list-menu.heads.name");
        CfgListMenu.headsLore = config.getStringList("list-menu.heads.lore");
        CfgListMenu.background1 = config.getSimpleItemStack("list-menu.background-1");
        CfgListMenu.background2 = config.getSimpleItemStack("list-menu.background-2");
        CfgListMenu.back = config.getSimpleItemStack("list-menu.back");
    }
}
