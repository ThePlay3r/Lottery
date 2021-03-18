package me.pljr.lottery.menus;

import lombok.Getter;
import me.pljr.lottery.config.Lang;
import me.pljr.lottery.config.MenuItemType;
import me.pljr.lottery.objects.LotteryPlayer;
import me.pljr.pljrapispigot.builders.GUIBuilder;
import me.pljr.pljrapispigot.builders.ItemBuilder;
import me.pljr.pljrapispigot.objects.GUI;
import me.pljr.pljrapispigot.objects.GUIItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerMenu {

    @Getter private final GUI gui;

    public PlayerMenu(LotteryPlayer lotteryPlayer){
        GUIBuilder builder = new GUIBuilder(Lang.MENU_TITLE.get(), 6);

        Player player = Bukkit.getPlayer(lotteryPlayer.getUniqueId());

        builder.setItem(0, MenuItemType.PLAYER_BACKGROUND_1.get());
        builder.setItem(9, MenuItemType.PLAYER_BACKGROUND_1.get());
        builder.setItem(18, MenuItemType.PLAYER_BACKGROUND_1.get());
        builder.setItem(19, MenuItemType.PLAYER_BACKGROUND_1.get());
        builder.setItem(28, MenuItemType.PLAYER_BACKGROUND_1.get());
        builder.setItem(34, MenuItemType.PLAYER_BACKGROUND_1.get());
        builder.setItem(25, MenuItemType.PLAYER_BACKGROUND_1.get());
        builder.setItem(35, MenuItemType.PLAYER_BACKGROUND_1.get());
        builder.setItem(44, MenuItemType.PLAYER_BACKGROUND_1.get());
        builder.setItem(53, MenuItemType.PLAYER_BACKGROUND_1.get());

        builder.setItem(5, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(6, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(11, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(12, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(17, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(26, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(27, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(36, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(47, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(48, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(41, MenuItemType.PLAYER_BACKGROUND_2.get());
        builder.setItem(42, MenuItemType.PLAYER_BACKGROUND_2.get());

        String playerName = player.getName();

        builder.setItem(22, new ItemBuilder(MenuItemType.PLAYER_HEAD.get())
                .withOwner(playerName)
                .replaceName("{name}", playerName)
                .create());
        builder.setItem(29, new ItemBuilder(MenuItemType.PLAYER_TICKETS.get())
                .replaceLore("{amount}", lotteryPlayer.getCurrentTickets()+"")
                .create());
        builder.setItem(31, new ItemBuilder(MenuItemType.PLAYER_BIGGEST_WIN.get())
                .replaceLore("{amount}", lotteryPlayer.getWonAmountMax()+"")
                .create());
        builder.setItem(33, new ItemBuilder(MenuItemType.PLAYER_EARNINGS.get())
                .replaceLore("{amount}", lotteryPlayer.getWonAmountTotal()+"")
                .create());
        builder.setItem(40, new GUIItem(MenuItemType.PLAYER_BACK.get(), click -> click.getWhoClicked().closeInventory()));

        gui = builder.create();
    }
}
