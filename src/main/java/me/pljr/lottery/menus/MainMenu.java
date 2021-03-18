package me.pljr.lottery.menus;

import lombok.Getter;
import me.pljr.lottery.config.Lang;
import me.pljr.lottery.config.MenuItemType;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.pljrapispigot.builders.GUIBuilder;
import me.pljr.pljrapispigot.builders.ItemBuilder;
import me.pljr.pljrapispigot.objects.GUI;
import me.pljr.pljrapispigot.objects.GUIItem;
import me.pljr.pljrapispigot.utils.FormatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class MainMenu {

    @Getter private final GUI gui;

    public MainMenu(Player player, GameLotteryManager lotteryManager){
        GUIBuilder builder = new GUIBuilder(Lang.MENU_TITLE.get(), 6);

        String playerName = player.getName();

        builder.setItem(0, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(1, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(9, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(15, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(16, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(25, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(28, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(37, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(38, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(44, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(52, MenuItemType.MAIN_BACKGROUND_1.get());
        builder.setItem(53, MenuItemType.MAIN_BACKGROUND_1.get());

        builder.setItem(7, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(8, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(11, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(12, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(18, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(27, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(26, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(35, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(41, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(42, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(45, MenuItemType.MAIN_BACKGROUND_2.get());
        builder.setItem(46, MenuItemType.MAIN_BACKGROUND_2.get());

        builder.setItem(4, new GUIItem(
                new ItemBuilder(MenuItemType.MAIN_HEAD.get())
                        .withOwner(playerName)
                        .replaceName("{name}", playerName)
                        .create(),
                click -> {
                    Player whoClicked = (Player) click.getWhoClicked();
                    whoClicked.closeInventory();
                    Bukkit.dispatchCommand(whoClicked, "lottery stats");
                }
        ));
        builder.setItem(20, new ItemBuilder(MenuItemType.MAIN_CURRENT_MONEY.get())
                .replaceLore("{amount}", lotteryManager.getCurrentLottery().getAmount()+"")
                .create());
        builder.setItem(29, new GUIItem(MenuItemType.MAIN_LIST.get(),
                click -> {
                    Player whoClicked = (Player) click.getWhoClicked();
                    whoClicked.closeInventory();
                    Bukkit.dispatchCommand(whoClicked, "lottery list");
                }));
        builder.setItem(22, MenuItemType.MAIN_INSTRUCTIONS.get());
        builder.setItem(31, MenuItemType.MAIN_HELP.get());
        builder.setItem(24, new GUIItem(MenuItemType.MAIN_BUY.get(),
                click -> {
                    Player whoClicked = (Player) click.getWhoClicked();
                    ClickType type = click.getClick();
                    if (type.equals(ClickType.LEFT)){
                        Bukkit.dispatchCommand(whoClicked, "lottery buy 1");
                    }else if (type.equals(ClickType.MIDDLE)){
                        Bukkit.dispatchCommand(whoClicked, "lottery buy 10");
                    }else if (type.equals(ClickType.RIGHT)){
                        Bukkit.dispatchCommand(whoClicked, "lottery buy 30");
                    }
                }));
        builder.setItem(33, new ItemBuilder(MenuItemType.MAIN_TIME_LEFT.get())
                .replaceLore("{time}", FormatUtil.formatTime(lotteryManager.getTime()))
                .create());

        gui = builder.create();
    }
}
