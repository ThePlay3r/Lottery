package me.pljr.lottery.config;

import me.pljr.lottery.Lottery;
import me.pljr.pljrapi.managers.ConfigManager;
import org.bukkit.inventory.ItemStack;

public class CfgConfirmMenu {
    private final static ConfigManager config = Lottery.getConfigManager();

    public static String title;
    public static ItemStack background;
    public static ItemStack confirm1;
    public static ItemStack confirm2;
    public static ItemStack information;
    public static ItemStack decline1;
    public static ItemStack decline2;
    public static ItemStack back;

    public static void load(){
        CfgConfirmMenu.title = config.getString("confirm-menu.title");
        CfgConfirmMenu.background = config.getSimpleItemStack("confirm-menu.background");
        CfgConfirmMenu.confirm1 = config.getSimpleItemStack("confirm-menu.confirm-1");
        CfgConfirmMenu.confirm2 = config.getSimpleItemStack("confirm-menu.confirm-2");
        CfgConfirmMenu.information = config.getSimpleItemStack("confirm-menu.information");
        CfgConfirmMenu.decline1 = config.getSimpleItemStack("confirm-menu.decline-1");
        CfgConfirmMenu.decline2 = config.getSimpleItemStack("confirm-menu.decline-2");
        CfgConfirmMenu.back = config.getSimpleItemStack("confirm-menu.back");
    }
}
