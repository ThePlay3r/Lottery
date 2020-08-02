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
import me.pljr.pljrapi.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            ChatUtil.broadcast(GameLotteryUtil.getChatStart());
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
        String playerName = player.getName();
        List<String> players = currentLottery.getPlayers();
        int amount = currentLottery.getAmount();
        players.add(playerName);
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
                    ChatUtil.broadcast(CfgBroadcast.chatNoWinner);
                }
                if (CfgSettings.broadcastActionBar){
                    ActionBarManager.broadcast(CfgBroadcast.actionBarNoWinner);
                }
                if (CfgSettings.broadcastTitle){
                    TitleManager.broadcast(CfgBroadcast.titleNoWinner);
                }
            }else{
                String winner = currentLottery.getPlayers().get(new Random().nextInt(currentLottery.getPlayers().size()));
                CorePlayer corePlayer = PlayerManager.getCorePlayer(winner);
                int amount = currentLottery.getAmount();
                if (CfgSettings.broadcastChat){
                    ChatUtil.broadcast(GameLotteryUtil.getChatEnd(winner, amount));
                }
                if (CfgSettings.broadcastActionBar){
                    ActionBarManager.broadcast(GameLotteryUtil.getActionBarEnd(winner, amount));
                }
                if (CfgSettings.broadcastTitle){
                    TitleManager.broadcast(GameLotteryUtil.getTitleEnd(winner, amount));
                }
                PLJRApi.getVaultEcon().depositPlayer(winner, amount);
                currentLottery.setPlayers(new ArrayList<>());
                corePlayer.setCurrentTickets(0);
                corePlayer.setWonAmountLast(amount);
                int maxWin = corePlayer.getWonAmountMax();
                int total = corePlayer.getWonAmountTotal();
                if (maxWin < amount){
                    corePlayer.setWonAmountMax(amount);
                }
                corePlayer.setWonAmountTotal(total+amount);
                if (PlayerUtil.isPlayer(winner)){
                    Player player = Bukkit.getPlayer(winner);
                    player.sendMessage(CfgLang.lang.get(Lang.PLAYER_WIN).replace("%winAmount", amount+""));
                }
                PlayerManager.setCorePlayer(winner, corePlayer);
                PlayerManager.savePlayer(winner);
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
