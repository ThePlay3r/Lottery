package me.pljr.lottery.utils;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.ActionBarType;
import me.pljr.lottery.config.CfgSettings;
import me.pljr.lottery.config.Lang;
import me.pljr.lottery.config.TitleType;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.pljrapispigot.builders.ActionBarBuilder;
import me.pljr.pljrapispigot.builders.TitleBuilder;
import me.pljr.pljrapispigot.objects.PLJRActionBar;
import me.pljr.pljrapispigot.objects.PLJRTitle;
import me.pljr.pljrapispigot.utils.VaultUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameLotteryUtil {

    public static String getChatStart() {
        return Lang.BROADCAST_START.get().replace("{cost}", CfgSettings.COST + "");
    }

    public static String getChatEnd(String winner, int winAmount) {
        return Lang.BROADCAST_END.get().replace("{winner}", winner).replace("{winAmount}", winAmount + "");
    }

    public static PLJRActionBar getActionBarStart() {
        return new ActionBarBuilder(ActionBarType.BROADCAST_START.get()).replaceMessage("{cost}", CfgSettings.COST + "").create();
    }

    public static PLJRActionBar getActionBarEnd(String winner, int winAmount) {
        return new ActionBarBuilder(ActionBarType.BROADCAST_END.get())
                .replaceMessage("{winAmount}", winAmount + "")
                .replaceMessage("{winner}", winner).create();
    }

    public static PLJRTitle getTitleStart() {
        return new TitleBuilder(TitleType.BROADCAST_START.get()).replaceSubtitle("{cost}", CfgSettings.COST + "").create();
    }

    public static PLJRTitle getTitleEnd(String winner, int winAmount){
        return new TitleBuilder(TitleType.BROADCAST_END.get())
                .replaceSubtitle("{winAmount}", winAmount + "")
                .replaceSubtitle("{winner}", winner).create();
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
