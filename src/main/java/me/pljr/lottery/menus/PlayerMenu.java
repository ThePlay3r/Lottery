package me.pljr.lottery.menus;

import me.pljr.lottery.config.CfgMainMenu;
import me.pljr.lottery.config.CfgPlayerMenu;
import me.pljr.lottery.managers.PlayerManager;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.pljrapi.managers.GuiManager;
import me.pljr.pljrapi.utils.FormatUtil;
import me.pljr.pljrapi.utils.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class PlayerMenu implements Listener {

    public static void open(Player player, String menuPlayer){
        Inventory inventory = Bukkit.createInventory(player, 6*9, CfgPlayerMenu.title);

        CorePlayer corePlayer = PlayerManager.getCorePlayer(menuPlayer);

        inventory.setItem(0, CfgPlayerMenu.background1);
        inventory.setItem(9, CfgPlayerMenu.background1);
        inventory.setItem(18, CfgPlayerMenu.background1);
        inventory.setItem(19, CfgPlayerMenu.background1);
        inventory.setItem(28, CfgPlayerMenu.background1);
        inventory.setItem(34, CfgPlayerMenu.background1);
        inventory.setItem(25, CfgPlayerMenu.background1);
        inventory.setItem(35, CfgPlayerMenu.background1);
        inventory.setItem(44, CfgPlayerMenu.background1);
        inventory.setItem(53, CfgPlayerMenu.background1);

        inventory.setItem(5, CfgPlayerMenu.background2);
        inventory.setItem(6, CfgPlayerMenu.background2);
        inventory.setItem(11, CfgPlayerMenu.background2);
        inventory.setItem(12, CfgPlayerMenu.background2);
        inventory.setItem(17, CfgPlayerMenu.background2);
        inventory.setItem(26, CfgPlayerMenu.background2);
        inventory.setItem(27, CfgPlayerMenu.background2);
        inventory.setItem(36, CfgPlayerMenu.background2);
        inventory.setItem(47, CfgPlayerMenu.background2);
        inventory.setItem(48, CfgPlayerMenu.background2);
        inventory.setItem(41, CfgPlayerMenu.background2);
        inventory.setItem(42, CfgPlayerMenu.background2);

        inventory.setItem(22, GuiManager.createHead(menuPlayer, CfgPlayerMenu.playerHeadName.replace("%name", menuPlayer)));
        inventory.setItem(29, ItemStackUtil.replaceLore(CfgPlayerMenu.ticketsBought, "%amount", corePlayer.getCurrentTickets()+""));
        inventory.setItem(31, ItemStackUtil.replaceLore(CfgPlayerMenu.biggestWin, "%amount", corePlayer.getWonAmountMax()+""));
        inventory.setItem(33, ItemStackUtil.replaceLore(CfgPlayerMenu.totalEarnings, "%amount", corePlayer.getWonAmountTotal()+""));
        inventory.setItem(40, CfgPlayerMenu.back);

        player.openInventory(inventory);
    }

    @EventHandler
    public static void onClick(InventoryClickEvent event){
        if (event.getView().getTitle().equals(CfgPlayerMenu.title)){
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player){
                Player player = (Player) event.getWhoClicked();
                int slot = event.getSlot();
                if (slot == 40){
                    player.closeInventory();
                    MainMenu.open(player);
                }
            }
        }
    }
}
