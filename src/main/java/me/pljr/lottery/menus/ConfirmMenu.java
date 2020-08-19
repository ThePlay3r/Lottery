package me.pljr.lottery.menus;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgConfirmMenu;
import me.pljr.lottery.config.CfgLang;
import me.pljr.lottery.enums.Lang;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.lottery.utils.GameLotteryUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class ConfirmMenu implements Listener {

    public static void open(Player player){
        Inventory inventory = Bukkit.createInventory(player, 6*9, CfgConfirmMenu.title);

        inventory.setItem(18, CfgConfirmMenu.background);
        inventory.setItem(19, CfgConfirmMenu.background);
        inventory.setItem(10, CfgConfirmMenu.background);
        inventory.setItem(11, CfgConfirmMenu.background);
        inventory.setItem(12, CfgConfirmMenu.background);
        inventory.setItem(13, CfgConfirmMenu.background);
        inventory.setItem(4, CfgConfirmMenu.background);
        inventory.setItem(5, CfgConfirmMenu.background);
        inventory.setItem(6, CfgConfirmMenu.background);
        inventory.setItem(7, CfgConfirmMenu.background);
        inventory.setItem(8, CfgConfirmMenu.background);
        inventory.setItem(49, CfgConfirmMenu.background);
        inventory.setItem(47, CfgConfirmMenu.background);
        inventory.setItem(46, CfgConfirmMenu.background);
        inventory.setItem(45, CfgConfirmMenu.background);
        inventory.setItem(40, CfgConfirmMenu.background);
        inventory.setItem(41, CfgConfirmMenu.background);
        inventory.setItem(42, CfgConfirmMenu.background);
        inventory.setItem(43, CfgConfirmMenu.background);
        inventory.setItem(44, CfgConfirmMenu.background);

        inventory.setItem(19, CfgConfirmMenu.confirm2);
        inventory.setItem(20, CfgConfirmMenu.confirm2);
        inventory.setItem(29, CfgConfirmMenu.confirm2);
        inventory.setItem(39, CfgConfirmMenu.confirm2);
        inventory.setItem(28, CfgConfirmMenu.confirm1);
        inventory.setItem(37, CfgConfirmMenu.confirm1);
        inventory.setItem(38, CfgConfirmMenu.confirm1);
        inventory.setItem(30, CfgConfirmMenu.confirm1);
        inventory.setItem(21, CfgConfirmMenu.confirm1);

        inventory.setItem(34, CfgConfirmMenu.decline2);
        inventory.setItem(33, CfgConfirmMenu.decline2);
        inventory.setItem(24, CfgConfirmMenu.decline2);
        inventory.setItem(14, CfgConfirmMenu.decline2);
        inventory.setItem(23, CfgConfirmMenu.decline1);
        inventory.setItem(32, CfgConfirmMenu.decline1);
        inventory.setItem(25, CfgConfirmMenu.decline1);
        inventory.setItem(16, CfgConfirmMenu.decline1);
        inventory.setItem(15, CfgConfirmMenu.decline1);

        inventory.setItem(22, CfgConfirmMenu.information);
        inventory.setItem(31, CfgConfirmMenu.back);

        player.openInventory(inventory);
    }

    @EventHandler
    public static void onClick(InventoryClickEvent event){
        if (event.getView().getTitle().equals(CfgConfirmMenu.title)){
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player){
                Player player = (Player) event.getWhoClicked();
                int slot = event.getSlot();
                if (slot == 19 ||slot == 20 ||slot == 29 ||slot == 39 ||slot == 28 ||slot == 37 ||slot == 38 ||slot == 30 ||slot == 21){
                    UUID playerId = player.getUniqueId();
                    CorePlayer corePlayer = Lottery.getPlayerManager().getCorePlayer(playerId);
                    player.closeInventory();
                    if (GameLotteryUtil.confirm(player)){
                        player.sendMessage(CfgLang.lang.get(Lang.BUY_SUCCESS).replace("%amount", corePlayer.getConfirmBuyAmount()+""));
                    }else{
                        player.sendMessage(CfgLang.lang.get(Lang.BUY_FAILURE).replace("%amount", corePlayer.getConfirmBuyAmount()+""));
                    }
                }else if (slot == 34 ||slot == 33 ||slot == 24 ||slot == 14 ||slot == 23 ||slot == 32 ||slot == 25 ||slot == 16 ||slot == 15){
                    player.closeInventory();
                    MainMenu.open(player);
                }else if (slot == 31){
                    player.closeInventory();
                    MainMenu.open(player);
                }
            }
        }
    }
}
