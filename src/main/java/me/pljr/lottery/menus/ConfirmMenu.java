package me.pljr.lottery.menus;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgConfirmMenu;
import me.pljr.lottery.config.Lang;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.lottery.utils.GameLotteryUtil;
import me.pljr.pljrapispigot.builders.GUIBuilder;
import me.pljr.pljrapispigot.managers.GUIManager;
import me.pljr.pljrapispigot.objects.GUI;
import me.pljr.pljrapispigot.objects.GUIItem;
import me.pljr.pljrapispigot.utils.ChatUtil;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ConfirmMenu {

    public static GUI get(Player player){
        GUIBuilder builder = new GUIBuilder(CfgConfirmMenu.TITLE, 6);

        builder.setItem(18, CfgConfirmMenu.BACKGROUND);
        builder.setItem(19, CfgConfirmMenu.BACKGROUND);
        builder.setItem(10, CfgConfirmMenu.BACKGROUND);
        builder.setItem(11, CfgConfirmMenu.BACKGROUND);
        builder.setItem(12, CfgConfirmMenu.BACKGROUND);
        builder.setItem(13, CfgConfirmMenu.BACKGROUND);
        builder.setItem(4, CfgConfirmMenu.BACKGROUND);
        builder.setItem(5, CfgConfirmMenu.BACKGROUND);
        builder.setItem(6, CfgConfirmMenu.BACKGROUND);
        builder.setItem(7, CfgConfirmMenu.BACKGROUND);
        builder.setItem(8, CfgConfirmMenu.BACKGROUND);
        builder.setItem(49, CfgConfirmMenu.BACKGROUND);
        builder.setItem(47, CfgConfirmMenu.BACKGROUND);
        builder.setItem(46, CfgConfirmMenu.BACKGROUND);
        builder.setItem(45, CfgConfirmMenu.BACKGROUND);
        builder.setItem(40, CfgConfirmMenu.BACKGROUND);
        builder.setItem(41, CfgConfirmMenu.BACKGROUND);
        builder.setItem(42, CfgConfirmMenu.BACKGROUND);
        builder.setItem(43, CfgConfirmMenu.BACKGROUND);
        builder.setItem(44, CfgConfirmMenu.BACKGROUND);

        GUIManager.ClickRunnable confirm = run -> {
            UUID playerId = player.getUniqueId();
            CorePlayer corePlayer = Lottery.getPlayerManager().getCorePlayer(playerId);
            player.closeInventory();
            if (GameLotteryUtil.confirm(player)){
                ChatUtil.sendMessage(player, Lang.BUY_SUCCESS.get().replace("{amount}", corePlayer.getConfirmBuyAmount()+""));
            }else{
                ChatUtil.sendMessage(player, Lang.BUY_FAILURE.get().replace("{amount}", corePlayer.getConfirmBuyAmount()+""));
            }
        };

        GUIManager.ClickRunnable decline = run -> {
            player.closeInventory();
            MainMenu.get(player).open(player);
        };

        GUIItem confirm1 = new GUIItem(CfgConfirmMenu.CONFIRM_1, confirm);
        GUIItem confirm2 = new GUIItem(CfgConfirmMenu.CONFIRM_2, confirm);

        builder.setItem(19, confirm2);
        builder.setItem(20, confirm2);
        builder.setItem(29, confirm2);
        builder.setItem(39, confirm2);
        builder.setItem(28, confirm1);
        builder.setItem(37, confirm1);
        builder.setItem(38, confirm1);
        builder.setItem(30, confirm1);
        builder.setItem(21, confirm1);

        GUIItem decline1 = new GUIItem(CfgConfirmMenu.DECLINE_1, confirm);
        GUIItem decline2 = new GUIItem(CfgConfirmMenu.DECLINE_2, confirm);

        builder.setItem(34, decline2);
        builder.setItem(33, decline2);
        builder.setItem(24, decline2);
        builder.setItem(14, decline2);
        builder.setItem(23, decline1);
        builder.setItem(32, decline1);
        builder.setItem(25, decline1);
        builder.setItem(16, decline1);
        builder.setItem(15, decline1);

        builder.setItem(22, CfgConfirmMenu.INFORMATION);
        builder.setItem(31, new GUIItem(CfgConfirmMenu.BACK, decline));

        return builder.create();
    }
}
