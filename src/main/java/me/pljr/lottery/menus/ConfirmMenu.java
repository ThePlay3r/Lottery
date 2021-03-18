package me.pljr.lottery.menus;

import lombok.Getter;
import me.pljr.lottery.config.Lang;
import me.pljr.lottery.config.MenuItemType;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.lottery.managers.PlayerManager;
import me.pljr.pljrapispigot.builders.GUIBuilder;
import me.pljr.pljrapispigot.managers.GUIManager;
import me.pljr.pljrapispigot.objects.GUI;
import me.pljr.pljrapispigot.objects.GUIItem;
import me.pljr.pljrapispigot.utils.ChatUtil;
import me.pljr.pljrapispigot.utils.VaultUtil;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ConfirmMenu {
    @Getter private final GUI gui;

    public ConfirmMenu(Player player, PlayerManager playerManager, GameLotteryManager gameLotteryManager, int amount, int cost){
        GUIBuilder builder = new GUIBuilder(Lang.MENU_TITLE.get(), 6);

        builder.setItem(18, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(19, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(10, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(11, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(12, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(13, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(4, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(5, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(6, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(7, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(8, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(49, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(47, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(46, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(45, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(40, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(41, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(42, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(43, MenuItemType.CONFIRM_BACKGROUND.get());
        builder.setItem(44, MenuItemType.CONFIRM_BACKGROUND.get());

        GUIManager.ClickRunnable confirm = run -> {
            UUID playerId = player.getUniqueId();
            playerManager.getPlayer(playerId, lotteryPlayer -> {
                VaultUtil.withdraw(player, cost);
                for (int i=0;i<amount;i++){
                    gameLotteryManager.add(player);
                }
                int currentTickets = lotteryPlayer.getCurrentTickets();
                lotteryPlayer.setCurrentTickets(currentTickets+amount);
                playerManager.setPlayer(playerId, lotteryPlayer);
                ChatUtil.sendMessage(player, Lang.BUY_SUCCESS.get().replace("{amount}", lotteryPlayer.getConfirmBuyAmount()+""));
            });
            player.closeInventory();
        };

        GUIManager.ClickRunnable decline = run -> {
            player.closeInventory();
            new MainMenu(player, gameLotteryManager).getGui().open(player);
        };

        GUIItem confirm1 = new GUIItem(MenuItemType.CONFIRM_CONFIRM_1.get(), confirm);
        GUIItem confirm2 = new GUIItem(MenuItemType.CONFIRM_CONFIRM_2.get(), confirm);

        builder.setItem(19, confirm2);
        builder.setItem(20, confirm2);
        builder.setItem(29, confirm2);
        builder.setItem(39, confirm2);
        builder.setItem(28, confirm1);
        builder.setItem(37, confirm1);
        builder.setItem(38, confirm1);
        builder.setItem(30, confirm1);
        builder.setItem(21, confirm1);

        GUIItem decline1 = new GUIItem(MenuItemType.CONFIRM_DECLINE_1.get(), decline);
        GUIItem decline2 = new GUIItem(MenuItemType.CONFIRM_DECLINE_2.get(), decline);

        builder.setItem(34, decline2);
        builder.setItem(33, decline2);
        builder.setItem(24, decline2);
        builder.setItem(14, decline2);
        builder.setItem(23, decline1);
        builder.setItem(32, decline1);
        builder.setItem(25, decline1);
        builder.setItem(16, decline1);
        builder.setItem(15, decline1);

        builder.setItem(22, MenuItemType.CONFIRM_INFORMATION.get());
        builder.setItem(31, new GUIItem(MenuItemType.CONFIRM_BACK.get(), decline));

        gui = builder.create();
    }
}
