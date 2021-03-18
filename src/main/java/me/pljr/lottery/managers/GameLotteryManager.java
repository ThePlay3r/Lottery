package me.pljr.lottery.managers;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.ActionBarType;
import me.pljr.lottery.config.Lang;
import me.pljr.lottery.config.Settings;
import me.pljr.lottery.config.TitleType;
import me.pljr.lottery.objects.GameLottery;
import me.pljr.pljrapispigot.builders.ActionBarBuilder;
import me.pljr.pljrapispigot.builders.TitleBuilder;
import me.pljr.pljrapispigot.utils.ChatUtil;
import me.pljr.pljrapispigot.utils.VaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GameLotteryManager {
    private final Settings settings;
    private final PlayerManager playerManager;

    private GameLottery currentLottery;
    private int time;

    public GameLotteryManager(Settings settings, PlayerManager playerManager){
        this.settings = settings;
        this.currentLottery = new GameLottery();
        this.time = settings.getCountdown();
        this.playerManager = playerManager;
    }

    public void start(){
        currentLottery = new GameLottery();
        if (settings.isBroadcastChat()){
            ChatUtil.broadcast(Lang.BROADCAST_START.get().replace("{cost}", settings.getCost() + ""), "", settings.isBungee());
        }
        if (settings.isBroadcastActionbar()){
            new ActionBarBuilder(ActionBarType.BROADCAST_START.get()).replaceMessage("{cost}", settings.getCost() + "").create().broadcast();
        }
        if (settings.isBroadcastTitle()){
            new TitleBuilder(TitleType.BROADCAST_START.get()).replaceSubtitle("{cost}", settings.getCost() + "").create().broadcast();
        }
        Bukkit.getScheduler().runTaskTimerAsynchronously(Lottery.get(), ()->{
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
        currentLottery.setAmount(amount + settings.getCost());
    }

    public void addMoney(int amount){
        int current = currentLottery.getAmount();
        currentLottery.setAmount(amount+current);
    }

    public void end(boolean selectWinner){
        Bukkit.getScheduler().cancelTasks(Lottery.get());
        time = settings.getCountdown();
        if (selectWinner){
            if (currentLottery.getPlayers().size() == 0){
                if (settings.isBroadcastChat()){
                    ChatUtil.broadcast(Lang.BROADCAST_NO_WINNER.get(), "", settings.isBungee());
                }
                if (settings.isBroadcastActionbar()){
                    ActionBarType.BROADCAST_NO_WINNER.get().broadcast();
                }
                if (settings.isBroadcastTitle()){
                    TitleType.BROADCAST_NO_WINNER.get().broadcast();
                }
            }else{
                Player winner = currentLottery.getPlayers().get(new Random().nextInt(currentLottery.getPlayers().size()));
                UUID winnerId = winner.getUniqueId();
                String winnerName = winner.getName();
                playerManager.getPlayer(winnerId, lotteryPlayer -> {
                    int amount = currentLottery.getAmount();
                    if (settings.isBroadcastChat()){
                        ChatUtil.broadcast(Lang.BROADCAST_END.get()
                                .replace("{winner}", winnerName).replace("{winAmount}", amount + ""), "", settings.isBungee());
                    }
                    if (settings.isBroadcastActionbar()){
                        new ActionBarBuilder(ActionBarType.BROADCAST_END.get())
                                .replaceMessage("{winAmount}", amount + "")
                                .replaceMessage("{winner}", winnerName).create().broadcast();
                    }
                    if (settings.isBroadcastTitle()){
                        new TitleBuilder(TitleType.BROADCAST_END.get())
                                .replaceSubtitle("{winAmount}", amount + "")
                                .replaceSubtitle("{winner}", winnerName).create().broadcast();
                    }
                    VaultUtil.deposit(winner, amount);
                    currentLottery.setPlayers(new ArrayList<>());
                    lotteryPlayer.setCurrentTickets(0);
                    lotteryPlayer.setWonAmountLast(amount);
                    int maxWin = lotteryPlayer.getWonAmountMax();
                    int total = lotteryPlayer.getWonAmountTotal();
                    if (maxWin < amount){
                        lotteryPlayer.setWonAmountMax(amount);
                    }
                    lotteryPlayer.setWonAmountTotal(total+amount);
                    if (winner.isOnline()){
                        ChatUtil.sendMessage(winner, Lang.PLAYER_WIN.get().replace("{winAmount}", amount+""));
                    }
                    playerManager.setPlayer(winnerId, lotteryPlayer);
                });
            }
        }
        if (settings.isRestartOnEnd()){
            Bukkit.getScheduler().runTaskLater(Lottery.get(), this::start, 20*15);
        }
    }

    public int getTime() {
        return time;
    }

    public GameLottery getCurrentLottery() {
        return currentLottery;
    }
}
