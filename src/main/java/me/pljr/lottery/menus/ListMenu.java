package me.pljr.lottery.menus;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgListMenu;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.pljrapi.XMaterial;
import me.pljr.pljrapi.XSound;
import me.pljr.pljrapi.managers.GuiManager;
import me.pljr.pljrapi.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ListMenu implements Listener {

    public static void open(Player player){
        Inventory inventory = Bukkit.createInventory(player, 6*9, CfgListMenu.title);

        GameLotteryManager lotteryManager = Lottery.getGameLotteryManager();

        int slot = 0;
        List<String> used = new ArrayList<>();
        for (Player lotteryPlayer : lotteryManager.getCurrentLottery().getPlayers()){
            if (slot>44) break;
            String playerName = lotteryPlayer.getName();
            if (used.contains(playerName)) continue;
            inventory.setItem(slot, GuiManager.createHead(playerName, CfgListMenu.headsName.replace("%name", playerName), CfgListMenu.headsLore));
            used.add(playerName);
            slot++;
        }

        inventory.setItem(45, CfgListMenu.background1);
        inventory.setItem(46, CfgListMenu.background1);
        inventory.setItem(47, CfgListMenu.background2);
        inventory.setItem(51, CfgListMenu.background2);
        inventory.setItem(52, CfgListMenu.background1);
        inventory.setItem(53, CfgListMenu.background1);

        inventory.setItem(49, CfgListMenu.back);

        player.openInventory(inventory);
    }

    @EventHandler
    public static void onClick(InventoryClickEvent event){
        if (event.getView().getTitle().equals(CfgListMenu.title)){
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player){
                Player player = (Player) event.getWhoClicked();
                int slot = event.getSlot();
                if (slot < 45 && slot > -1){
                    ItemStack itemStack = event.getCurrentItem();
                    if (itemStack == null) return;
                    if (itemStack.getType().equals(XMaterial.PLAYER_HEAD.parseMaterial())){
                        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                        String owner = skullMeta.getOwner();
                        if (PlayerUtil.isPlayer(owner)){
                            PlayerMenu.open(player, Bukkit.getPlayer(owner));
                        }else{
                            player.playSound(player.getLocation(), XSound.ENTITY_VILLAGER_NO.parseSound(), 1, 1);
                        }
                    }
                }else if (slot == 49){
                    MainMenu.open(player);
                }
            }
        }
    }
}
