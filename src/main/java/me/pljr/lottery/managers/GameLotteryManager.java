package me.pljr.lottery.managers;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgBroadcast;
import me.pljr.lottery.config.CfgSettings;
import me.pljr.lottery.config.Lang;
import me.pljr.lottery.objects.CorePlayer;
import me.pljr.lottery.objects.GameLottery;
import me.pljr.lottery.utils.GameLotteryUtil;
import me.pljr.pljrapispigot.managers.ActionBarManager;
import me.pljr.pljrapispigot.managers.TitleManager;
import me.pljr.pljrapispigot.utils.ChatUtil;
import me.pljr.pljrapispigot.utils.VaultUtil;
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
        this.time = CfgSettings.COUNTDOWN;
    }

    public void start(){
        currentLottery = new GameLottery();
        if (CfgSettings.BROADCAST_CHAT){
            ChatUtil.broadcast(GameLotteryUtil.getChatStart(), "", CfgSettings.BUNGEE);
        }
        if (CfgSettings.BROADCAST_ACTIONBAR){
            ActionBarManager.broadcast(GameLotteryUtil.getActionBarStart());
        }
        if (CfgSettings.BROADCAST_TITLE){
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
        currentLottery.setAmount(amount+CfgSettings.COST);
    }

    public void addMoney(int amount){
        int current = currentLottery.getAmount();
        currentLottery.setAmount(amount+current);
    }

    public void end(boolean selectWinner){
        Bukkit.getScheduler().cancelTasks(Lottery.getInstance());
        time = CfgSettings.COUNTDOWN;
        if (selectWinner){
            if (currentLottery.getPlayers().size() == 0){
                if (CfgSettings.BROADCAST_CHAT){
                    ChatUtil.broadcast(CfgBroadcast.CHAT_NO_WINNER, "", CfgSettings.BUNGEE);
                }
                if (CfgSettings.BROADCAST_ACTIONBAR){
                    ActionBarManager.broadcast(CfgBroadcast.ACTIONBAR_NO_WINNER);
                }
                if (CfgSettings.BROADCAST_TITLE){
                    TitleManager.broadcast(CfgBroadcast.TITLE_NO_WINNER);
                }
            }else{
                Player winner = currentLottery.getPlayers().get(new Random().nextInt(currentLottery.getPlayers().size()));
                UUID winnerId = winner.getUniqueId();
                String winnerName = winner.getName();
                CorePlayer corePlayer = Lottery.getPlayerManager().getCorePlayer(winnerId);
                int amount = currentLottery.getAmount();
                if (CfgSettings.BROADCAST_CHAT){
                    ChatUtil.broadcast(GameLotteryUtil.getChatEnd(winnerName, amount), "", CfgSettings.BUNGEE);
                }
                if (CfgSettings.BROADCAST_ACTIONBAR){
                    ActionBarManager.broadcast(GameLotteryUtil.getActionBarEnd(winnerName, amount));
                }
                if (CfgSettings.BROADCAST_TITLE){
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
                    ChatUtil.sendMessage(winner, Lang.PLAYER_WIN.get().replace("{winAmount}", amount+""));
                }
                Lottery.getPlayerManager().setCorePlayer(winnerId, corePlayer);
            }
        }
        if (CfgSettings.RESTART_ON_END){
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
