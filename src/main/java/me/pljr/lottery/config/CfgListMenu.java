package me.pljr.lottery.config;

import me.pljr.pljrapispigot.managers.ConfigManager;
import org.bukkit.inventory.ItemStack;

public class CfgListMenu {
    public static String TITLE;
    public static ItemStack HEADS;
    public static ItemStack BACKGROUND_1;
    public static ItemStack BACKGROUND_2;
    public static ItemStack BACK;

    public static void load(ConfigManager config){
        CfgListMenu.TITLE = config.getString("list-menu.title");
        CfgListMenu.HEADS = config.getHead("list-menu.heads");
        CfgListMenu.BACKGROUND_1 = config.getSimpleItemStack("list-menu.background-1");
        CfgListMenu.BACKGROUND_2 = config.getSimpleItemStack("list-menu.background-2");
        CfgListMenu.BACK = config.getSimpleItemStack("list-menu.back");
    }
}
