package me.pljr.lottery.managers;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgBroadcast;
import me.pljr.lottery.config.CfgLang;
import me.pljr.lottery.config.CfgSettings;
import me.pljr.lottery.enums.Lang;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.lottery.objects.GameLottery;
import me.pljr.lottery.utils.GameLotteryUtil;
import me.pljr.pljrapi.PLJRApi;
import me.pljr.pljrapi.managers.ActionBarManager;
import me.pljr.pljrapi.managers.TitleManager;
import me.pljr.pljrapi.utils.ChatUtil;
import me.pljr.pljrapi.utils.VaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GameLotteryManager {
    private GameLottery currentLottery;
    private int time;

    public GameLotteryManager(){
        this.currentLottery = new GameLottery();
        this.time = CfgSettings.countdown;
    }

    public void start(){
        currentLottery = new GameLottery();
        if (CfgSettings.broadcastChat){
            ChatUtil.broadcast(GameLotteryUtil.getChatStart(), "", CfgSettings.bungee);
        }
        if (CfgSettings.broadcastActionBar){
            ActionBarManager.broadcast(GameLotteryUtil.getActionBarStart());
        }
        if (CfgSettings.broadcastTitle){
            TitleManager.broadcast(GameLotteryUtil.getTitleStart());
        }
        Bukkit.getScheduler().runTaskTimerAsynchronously(Lottery.getInstance(), ()->{
            if (getTime() == 0){
                end(true);
            }
            time--;
        }, 0 ,20);
    }

    public void add(Player player){
        List<Player> players = currentLottery.getPlayers();
        int amount = currentLottery.getAmount();
        players.add(player);
        currentLottery.setPlayers(players);
        currentLottery.setAmount(amount+CfgSettings.cost);
    }

    public void addMoney(int amount){
        int current = currentLottery.getAmount();
        currentLottery.setAmount(amount+current);
    }

    public void end(boolean selectWinner){
        Bukkit.getScheduler().cancelTasks(Lottery.getInstance());
        time = CfgSettings.countdown;
        if (selectWinner){
            if (currentLottery.getPlayers().size() == 0){
                if (CfgSettings.broadcastChat){
                    ChatUtil.broadcast(CfgBroadcast.chatNoWinner, "", CfgSettings.bungee);
                }
                if (CfgSettings.broadcastActionBar){
                    ActionBarManager.broadcast(CfgBroadcast.actionBarNoWinner);
                }
                if (CfgSettings.broadcastTitle){
                    TitleManager.broadcast(CfgBroadcast.titleNoWinner);
                }
            }else{
                Player winner = currentLottery.getPlayers().get(new Random().nextInt(currentLottery.getPlayers().size()));
                UUID winnerId = winner.getUniqueId();
                String winnerName = winner.getName();
                CorePlayer corePlayer = Lottery.getPlayerManager().getCorePlayer(winnerId);
                int amount = currentLottery.getAmount();
                if (CfgSettings.broadcastChat){
                    ChatUtil.broadcast(GameLotteryUtil.getChatEnd(winnerName, amount), "", CfgSettings.bungee);
                }
                if (CfgSettings.broadcastActionBar){
                    ActionBarManager.broadcast(GameLotteryUtil.getActionBarEnd(winnerName, amount));
                }
                if (CfgSettings.broadcastTitle){
                    TitleManager.broadcast(GameLotteryUtil.getTitleEnd(winnerName, amount));
                }
                VaultUtil.deposit(winner, amount);
                currentLottery.setPlayers(new ArrayList<>());
                corePlayer.setCurrentTickets(0);
                corePlayer.setWonAmountLast(amount);
                int maxWin = corePlayer.getWonAmountMax();
                int total = corePlayer.getWonAmountTotal();
                if (maxWin < amount){
                    corePlayer.setWonAmountMax(amount);
                }
                corePlayer.setWonAmountTotal(total+amount);
                if (winner.isOnline()){
                    ChatUtil.sendMessage(winner, CfgLang.lang.get(Lang.PLAYER_WIN).replace("%winAmount", amount+""));
                }
                Lottery.getPlayerManager().setCorePlayer(winnerId, corePlayer);
            }
        }
        if (CfgSettings.restartOnEnd){
            Bukkit.getScheduler().runTaskLater(Lottery.getInstance(), this::start, 20*15);
        }
    }

    public int getTime() {
        return time;
    }

    public GameLottery getCurrentLottery() {
        return currentLottery;
    }
}
