package me.pljr.lottery.utils;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgBroadcast;
import me.pljr.lottery.config.CfgSettings;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.pljrapi.objects.PLJRActionBar;
import me.pljr.pljrapi.objects.PLJRTitle;
import me.pljr.pljrapi.utils.VaultUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameLotteryUtil {

    public static List<String> getChatStart() {
        List<String> broadcast = new ArrayList<>();
        for (String message : CfgBroadcast.chatStart) {
            broadcast.add(message.replace("%cost", CfgSettings.cost + ""));
        }
        return broadcast;
    }

    public static List<String> getChatEnd(String winner, int winAmount) {
        List<String> broadcast = new ArrayList<>();
        for (String message : CfgBroadcast.chatEnd) {
            broadcast.add(message.replace("%winner", winner).replace("%winAmount", winAmount + ""));
        }
        return broadcast;
    }

    public static PLJRActionBar getActionBarStart() {
        return new PLJRActionBar(CfgBroadcast.actionBarStart.getMessage().replace("%cost", CfgSettings.cost + ""), CfgBroadcast.actionBarStart.getDuration());
    }

    public static PLJRActionBar getActionBarEnd(String winner, int winAmount) {
        return new PLJRActionBar(CfgBroadcast.actionBarEnd.getMessage().replace("%winner", winner).replace("%winAmount", winAmount + ""), CfgBroadcast.actionBarEnd.getDuration());
    }

    public static PLJRTitle getTitleStart() {
        return new PLJRTitle(
                CfgBroadcast.titleStart.getTitle().replace("%cost", CfgSettings.cost + ""),
                CfgBroadcast.titleStart.getSubtitle().replace("%cost", CfgSettings.cost + ""),
                CfgBroadcast.titleStart.getIn(), CfgBroadcast.titleStart.getStay(), CfgBroadcast.titleStart.getOut()
        );
    }

    public static PLJRTitle getTitleEnd(String winner, int winAmount){
        return new PLJRTitle(
                CfgBroadcast.titleEnd.getTitle().replace("%winner", winner).replace("%winAmount", winAmount + ""),
                CfgBroadcast.titleEnd.getSubtitle().replace("%winner", winner).replace("%winAmount", winAmount + ""),
                CfgBroadcast.titleEnd.getIn(), CfgBroadcast.titleEnd.getStay(), CfgBroadcast.titleEnd.getOut()
        );
    }

    public static boolean buy(Player player, int amount){
        UUID playerId = player.getUniqueId();
        int cost = amount*CfgSettings.cost;
        double pMoney = VaultUtil.getBalance(player);
        if (CfgSettings.confirmation){
            CorePlayer corePlayer = Lottery.getPlayerManager().getCorePlayer(playerId);
            corePlayer.setConfirmBuyAmount(amount);
            Lottery.getPlayerManager().setCorePlayer(playerId, corePlayer);
        }else{
            if (pMoney >= cost){
                VaultUtil.withdraw(player, cost);
                Lottery.getGameLotteryManager().add(player);
            }
        }
        return pMoney >= cost;
    }

    public static boolean confirm(Player player){
        UUID playerId = player.getUniqueId();
        double pMoney = VaultUtil.getBalance(player);
        int amount = Lottery.getPlayerManager().getCorePlayer(playerId).getConfirmBuyAmount();
        int cost = amount*CfgSettings.cost;
        if (pMoney >= cost){
            CorePlayer corePlayer = Lottery.getPlayerManager().getCorePlayer(playerId);
            VaultUtil.withdraw(player, cost);
            for (int i=0;i<amount;i++){
                Lottery.getGameLotteryManager().add(player);
            }
            int currentTickets = corePlayer.getCurrentTickets();
            corePlayer.setCurrentTickets(currentTickets+amount);
            Lottery.getPlayerManager().setCorePlayer(playerId, corePlayer);
            return true;
        }
        return false;
    }
}
