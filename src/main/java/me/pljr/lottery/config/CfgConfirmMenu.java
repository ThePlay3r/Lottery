package me.pljr.lottery.config;

import me.pljr.pljrapispigot.managers.ConfigManager;
import org.bukkit.inventory.ItemStack;

public class CfgConfirmMenu {
    public static String TITLE;
    public static ItemStack BACKGROUND;
    public static ItemStack CONFIRM_1;
    public static ItemStack CONFIRM_2;
    public static ItemStack INFORMATION;
    public static ItemStack DECLINE_1;
    public static ItemStack DECLINE_2;
    public static ItemStack BACK;

    public static void load(ConfigManager config){
        CfgConfirmMenu.TITLE = config.getString("confirm-menu.title");
        CfgConfirmMenu.BACKGROUND = config.getSimpleItemStack("confirm-menu.background");
        CfgConfirmMenu.CONFIRM_1 = config.getSimpleItemStack("confirm-menu.confirm-1");
        CfgConfirmMenu.CONFIRM_2 = config.getSimpleItemStack("confirm-menu.confirm-2");
        CfgConfirmMenu.INFORMATION = config.getSimpleItemStack("confirm-menu.information");
        CfgConfirmMenu.DECLINE_1 = config.getSimpleItemStack("confirm-menu.decline-1");
        CfgConfirmMenu.DECLINE_2 = config.getSimpleItemStack("confirm-menu.decline-2");
        CfgConfirmMenu.BACK = config.getSimpleItemStack("confirm-menu.back");
    }
}
