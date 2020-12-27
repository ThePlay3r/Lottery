package me.pljr.lottery.menus;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgMainMenu;
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

    public static GUI get(Player player){
        GUIBuilder builder = new GUIBuilder(CfgMainMenu.TITLE, 6);

        GameLotteryManager lotteryManager = Lottery.getGameLotteryManager();
        String playerName = player.getName();

        builder.setItem(0, CfgMainMenu.BACKGROUND_1);
        builder.setItem(1, CfgMainMenu.BACKGROUND_1);
        builder.setItem(9, CfgMainMenu.BACKGROUND_1);
        builder.setItem(15, CfgMainMenu.BACKGROUND_1);
        builder.setItem(16, CfgMainMenu.BACKGROUND_1);
        builder.setItem(25, CfgMainMenu.BACKGROUND_1);
        builder.setItem(28, CfgMainMenu.BACKGROUND_1);
        builder.setItem(37, CfgMainMenu.BACKGROUND_1);
        builder.setItem(38, CfgMainMenu.BACKGROUND_1);
        builder.setItem(44, CfgMainMenu.BACKGROUND_1);
        builder.setItem(52, CfgMainMenu.BACKGROUND_1);
        builder.setItem(53, CfgMainMenu.BACKGROUND_1);

        builder.setItem(7, CfgMainMenu.BACKGROUND_2);
        builder.setItem(8, CfgMainMenu.BACKGROUND_2);
        builder.setItem(11, CfgMainMenu.BACKGROUND_2);
        builder.setItem(12, CfgMainMenu.BACKGROUND_2);
        builder.setItem(18, CfgMainMenu.BACKGROUND_2);
        builder.setItem(27, CfgMainMenu.BACKGROUND_2);
        builder.setItem(26, CfgMainMenu.BACKGROUND_2);
        builder.setItem(35, CfgMainMenu.BACKGROUND_2);
        builder.setItem(41, CfgMainMenu.BACKGROUND_2);
        builder.setItem(42, CfgMainMenu.BACKGROUND_2);
        builder.setItem(45, CfgMainMenu.BACKGROUND_2);
        builder.setItem(46, CfgMainMenu.BACKGROUND_2);

        builder.setItem(4, new GUIItem(
                new ItemBuilder(CfgMainMenu.PLAYER_HEAD)
                        .withOwner(playerName)
                        .replaceName("{name}", playerName)
                        .create(),
                run -> {
                    player.closeInventory();
                    Bukkit.dispatchCommand(player, "lottery stats");
                }
        ));
        builder.setItem(20, new ItemBuilder(CfgMainMenu.CURRENT_MONEY)
                .replaceLore("{amount}", lotteryManager.getCurrentLottery().getAmount()+"")
                .create());
        builder.setItem(29, new GUIItem(CfgMainMenu.LIST,
                run -> {
                    player.closeInventory();
                    Bukkit.dispatchCommand(player, "lottery list");
                }));
        builder.setItem(22, CfgMainMenu.INSTRUCTIONS);
        builder.setItem(31, CfgMainMenu.HELP);
        builder.setItem(24, new GUIItem(CfgMainMenu.BUY,
                run -> {
                    ClickType type = run.getClick();
                    if (type.equals(ClickType.LEFT)){
                        Bukkit.dispatchCommand(player, "lottery buy 1");
                    }else if (type.equals(ClickType.MIDDLE)){
                        Bukkit.dispatchCommand(player, "lottery buy 10");
                    }else if (type.equals(ClickType.RIGHT)){
                        Bukkit.dispatchCommand(player, "lottery buy 30");
                    }
                }));
        builder.setItem(33, new ItemBuilder(CfgMainMenu.TIME_LEFT)
                .replaceLore("{time}", FormatUtil.formatTime(lotteryManager.getTime()))
                .create());

        return builder.create();
    }
}
