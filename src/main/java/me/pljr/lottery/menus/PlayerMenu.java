package me.pljr.lottery.menus;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgPlayerMenu;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.pljrapispigot.builders.GUIBuilder;
import me.pljr.pljrapispigot.builders.ItemBuilder;
import me.pljr.pljrapispigot.objects.GUI;
import me.pljr.pljrapispigot.objects.GUIItem;
import org.bukkit.entity.Player;

public class PlayerMenu {

    public static GUI get(Player player, Player menuPlayer){
        GUIBuilder builder = new GUIBuilder(CfgPlayerMenu.TITLE, 6);

        CorePlayer corePlayer = Lottery.getPlayerManager().getCorePlayer(menuPlayer.getUniqueId());

        builder.setItem(0, CfgPlayerMenu.BACKGROUND_1);
        builder.setItem(9, CfgPlayerMenu.BACKGROUND_1);
        builder.setItem(18, CfgPlayerMenu.BACKGROUND_1);
        builder.setItem(19, CfgPlayerMenu.BACKGROUND_1);
        builder.setItem(28, CfgPlayerMenu.BACKGROUND_1);
        builder.setItem(34, CfgPlayerMenu.BACKGROUND_1);
        builder.setItem(25, CfgPlayerMenu.BACKGROUND_1);
        builder.setItem(35, CfgPlayerMenu.BACKGROUND_1);
        builder.setItem(44, CfgPlayerMenu.BACKGROUND_1);
        builder.setItem(53, CfgPlayerMenu.BACKGROUND_1);

        builder.setItem(5, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(6, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(11, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(12, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(17, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(26, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(27, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(36, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(47, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(48, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(41, CfgPlayerMenu.BACKGROUND_2);
        builder.setItem(42, CfgPlayerMenu.BACKGROUND_2);

        String playerName = menuPlayer.getName();

        builder.setItem(22, new ItemBuilder(CfgPlayerMenu.PLAYER_HEAD)
                .withOwner(playerName)
                .replaceName("{name}", playerName)
                .create());
        builder.setItem(29, new ItemBuilder(CfgPlayerMenu.TICKETS_BOUGHT)
                .replaceLore("{amount}", corePlayer.getCurrentTickets()+"")
                .create());
        builder.setItem(31, new ItemBuilder(CfgPlayerMenu.BIGGEST_WIN)
                .replaceLore("{amount}", corePlayer.getWonAmountMax()+"")
                .create());
        builder.setItem(33, new ItemBuilder(CfgPlayerMenu.TOTAL_EARNINGS)
                .replaceLore("{amount}", corePlayer.getWonAmountTotal()+"")
                .create());
        builder.setItem(40, new GUIItem(CfgPlayerMenu.BACK, run -> player.closeInventory()));

        return builder.create();
    }
}
