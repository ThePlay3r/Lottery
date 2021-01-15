package me.pljr.lottery.menus;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.Lang;
import me.pljr.lottery.config.MenuItemType;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.pljrapispigot.builders.GUIBuilder;
import me.pljr.pljrapispigot.builders.ItemBuilder;
import me.pljr.pljrapispigot.objects.GUI;
import me.pljr.pljrapispigot.objects.GUIItem;
import me.pljr.pljrapispigot.utils.PlayerUtil;
import me.pljr.pljrapispigot.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ListMenu {

    public static GUI get(Player player){
        GUIBuilder builder = new GUIBuilder(Lang.MENU_TITLE.get(), 6);
        GameLotteryManager lotteryManager = Lottery.getGameLotteryManager();

        int slot = 0;
        List<String> used = new ArrayList<>();
        for (Player lotteryPlayer : lotteryManager.getCurrentLottery().getPlayers()){
            if (slot>44) break;
            String lotteryPlayerName = lotteryPlayer.getName();
            if (used.contains(lotteryPlayerName)) continue;
            ItemStack item = new ItemBuilder(MenuItemType.LIST_HEADS.get())
                    .withOwner(lotteryPlayerName)
                    .replaceName("{name}", lotteryPlayerName)
                    .create();
            builder.setItem(slot, new GUIItem(item,
                    run -> {
                        if (PlayerUtil.isPlayer(lotteryPlayerName)){
                            PlayerMenu.get(player, Bukkit.getPlayer(lotteryPlayerName)).open(player);
                        }else{
                            player.playSound(player.getLocation(), XSound.ENTITY_VILLAGER_NO.parseSound(), 1, 1);
                        }
                    }));
            used.add(lotteryPlayerName);
            slot++;
        }

        builder.setItem(45, MenuItemType.LIST_BACKGROUND_1.get());
        builder.setItem(46, MenuItemType.LIST_BACKGROUND_1.get());
        builder.setItem(47, MenuItemType.LIST_BACKGROUND_2.get());
        builder.setItem(51, MenuItemType.LIST_BACKGROUND_2.get());
        builder.setItem(52, MenuItemType.LIST_BACKGROUND_1.get());
        builder.setItem(53, MenuItemType.LIST_BACKGROUND_1.get());

        builder.setItem(49, new GUIItem(MenuItemType.LIST_BACK.get(), run -> MainMenu.get(player).open(player)));

        return builder.create();
    }
}
