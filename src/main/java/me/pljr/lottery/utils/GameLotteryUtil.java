package me.pljr.lottery.utils;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgBroadcast;
import me.pljr.lottery.config.CfgSettings;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.pljrapispigot.objects.PLJRActionBar;
import me.pljr.pljrapispigot.objects.PLJRTitle;
import me.pljr.pljrapispigot.utils.VaultUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameLotteryUtil {

    public static List<String> getChatStart() {
        List<String> broadcast = new ArrayList<>();
        for (String message : CfgBroadcast.CHAT_START) {
            broadcast.add(message.replace("{cost}", CfgSettings.COST + ""));
        }
        return broadcast;
    }

    public static List<String> getChatEnd(String winner, int winAmount) {
        List<String> broadcast = new ArrayList<>();
        for (String message : CfgBroadcast.CHAT_END) {
            broadcast.add(message.replace("{winner}", winner).replace("{winAmount}", winAmount + ""));
        }
        return broadcast;
    }

    public static PLJRActionBar getActionBarStart() {
        return new PLJRActionBar(CfgBroadcast.ACTIONBAR_START.getMessage().replace("{cost}", CfgSettings.COST + ""), CfgBroadcast.ACTIONBAR_START.getDuration());
    }

    public static PLJRActionBar getActionBarEnd(String winner, int winAmount) {
        return new PLJRActionBar(CfgBroadcast.ACTIONBAR_END.getMessage().replace("{winner}", winner).replace("{winAmount}", winAmount + ""), CfgBroadcast.ACTIONBAR_END.getDuration());
    }

    public static PLJRTitle getTitleStart() {
        return new PLJRTitle(
                CfgBroadcast.TITLE_START.getTitle().replace("{cost}", CfgSettings.COST + ""),
                CfgBroadcast.TITLE_START.getSubtitle().replace("{cost}", CfgSettings.COST + ""),
                CfgBroadcast.TITLE_START.getIn(), CfgBroadcast.TITLE_START.getStay(), CfgBroadcast.TITLE_START.getOut()
        );
    }

    public static PLJRTitle getTitleEnd(String winner, int winAmount){
        return new PLJRTitle(
                CfgBroadcast.TITLE_END.getTitle().replace("{winner}", winner).replace("{winAmount}", winAmount + ""),
                CfgBroadcast.TITLE_END.getSubtitle().replace("{winner}", winner).replace("{winAmount}", winAmount + ""),
                CfgBroadcast.TITLE_END.getIn(), CfgBroadcast.TITLE_END.getStay(), CfgBroadcast.TITLE_END.getOut()
        );
    }

    public static boolean buy(Player player, int amount){
        UUID playerId = player.getUniqueId();
        int cost = amount*CfgSettings.COST;
        double pMoney = VaultUtil.getBalance(player);
        if (CfgSettings.CONFIRMATION){
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
        int cost = amount*CfgSettings.COST;
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
