package me.pljr.lottery.menus;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgMainMenu;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.pljrapi.managers.GuiManager;
import me.pljr.pljrapi.utils.FormatUtil;
import me.pljr.pljrapi.utils.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MainMenu implements Listener {

    public static void open(Player player){
        Inventory inventory = Bukkit.createInventory(player, 6*9, CfgMainMenu.title);
        inventory.clear();

        GameLotteryManager lotteryManager = Lottery.getGameLotteryManager();
        String playerName = player.getName();

        inventory.setItem(0, CfgMainMenu.background1);
        inventory.setItem(1, CfgMainMenu.background1);
        inventory.setItem(9, CfgMainMenu.background1);
        inventory.setItem(15, CfgMainMenu.background1);
        inventory.setItem(16, CfgMainMenu.background1);
        inventory.setItem(25, CfgMainMenu.background1);
        inventory.setItem(28, CfgMainMenu.background1);
        inventory.setItem(37, CfgMainMenu.background1);
        inventory.setItem(38, CfgMainMenu.background1);
        inventory.setItem(44, CfgMainMenu.background1);
        inventory.setItem(52, CfgMainMenu.background1);
        inventory.setItem(53, CfgMainMenu.background1);

        inventory.setItem(7, CfgMainMenu.background2);
        inventory.setItem(8, CfgMainMenu.background2);
        inventory.setItem(11, CfgMainMenu.background2);
        inventory.setItem(12, CfgMainMenu.background2);
        inventory.setItem(18, CfgMainMenu.background2);
        inventory.setItem(27, CfgMainMenu.background2);
        inventory.setItem(26, CfgMainMenu.background2);
        inventory.setItem(35, CfgMainMenu.background2);
        inventory.setItem(41, CfgMainMenu.background2);
        inventory.setItem(42, CfgMainMenu.background2);
        inventory.setItem(45, CfgMainMenu.background2);
        inventory.setItem(46, CfgMainMenu.background2);

        inventory.setItem(4, GuiManager.createHead(playerName, CfgMainMenu.playerHeadName.replace("%name", playerName), CfgMainMenu.playerHeadLore));
        inventory.setItem(20, ItemStackUtil.replaceLore(CfgMainMenu.currentMoney, "%amount", lotteryManager.getCurrentLottery().getAmount()+""));
        inventory.setItem(29, CfgMainMenu.list);
        inventory.setItem(22, CfgMainMenu.instructions);
        inventory.setItem(31, CfgMainMenu.help);
        inventory.setItem(24, CfgMainMenu.buy);
        inventory.setItem(33, ItemStackUtil.replaceLore(CfgMainMenu.timeLeft, "%time", FormatUtil.formatTime(lotteryManager.getTime())));

        player.openInventory(inventory);
    }

    @EventHandler
    public static void onClick(InventoryClickEvent event){
        if (event.getView().getTitle().equals(CfgMainMenu.title)){
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player){
                Player player = (Player) event.getWhoClicked();
                int slot = event.getSlot();
                switch (slot){
                    case 4:
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "lottery stats");
                        break;
                    case 29:
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "lottery list");
                        break;
                    case 24:
                        ClickType type = event.getClick();
                        if (type.equals(ClickType.LEFT)){
                            Bukkit.dispatchCommand(player, "lottery buy 1");
                        }else if (type.equals(ClickType.MIDDLE)){
                            Bukkit.dispatchCommand(player, "lottery buy 10");
                        }else if (type.equals(ClickType.RIGHT)){
                            Bukkit.dispatchCommand(player, "lottery buy 30");
                        }
                        break;
                }
            }
        }
    }
}
