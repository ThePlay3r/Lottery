package me.pljr.lottery.menus;

import lombok.Getter;
import me.pljr.lottery.config.Lang;
import me.pljr.lottery.config.MenuItemType;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.lottery.objects.LotteryPlayer;
import me.pljr.pljrapispigot.builders.GUIBuilder;
import me.pljr.pljrapispigot.builders.ItemBuilder;
import me.pljr.pljrapispigot.objects.GUI;
import me.pljr.pljrapispigot.objects.GUIItem;
import me.pljr.pljrapispigot.utils.PlayerUtil;
import me.pljr.pljrapispigot.xseries.XSound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ListMenu {

    @Getter private final GUI gui;

    public ListMenu(LotteryPlayer lotteryPlayer, GameLotteryManager lotteryManager){
        GUIBuilder builder = new GUIBuilder(Lang.MENU_TITLE.get(), 6);

        int slot = 0;
        List<String> used = new ArrayList<>();
        for (Player inLotteryPlayer : lotteryManager.getCurrentLottery().getPlayers()){
            if (slot>44) break;
            String lotteryPlayerName = inLotteryPlayer.getName();
            if (used.contains(lotteryPlayerName)) continue;
            ItemStack item = new ItemBuilder(MenuItemType.LIST_HEADS.get())
                    .withOwner(lotteryPlayerName)
                    .replaceName("{name}", lotteryPlayerName)
                    .create();
            builder.setItem(slot, new GUIItem(item,
                    click -> {
                        Player whoClicked = (Player) click.getWhoClicked();
                        if (PlayerUtil.isPlayer(lotteryPlayerName)){
                            new PlayerMenu(lotteryPlayer).getGui().open(whoClicked);
                        }else{
                            whoClicked.playSound(whoClicked.getLocation(), XSound.ENTITY_VILLAGER_NO.parseSound(), 1, 1);
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

        builder.setItem(49, new GUIItem(MenuItemType.LIST_BACK.get(), click -> {
            Player whoClicked = (Player) click.getWhoClicked();
            new MainMenu(whoClicked, lotteryManager).getGui().open(whoClicked);
        }));

        gui = builder.create();
    }
}
