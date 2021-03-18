package me.pljr.lottery.commands;

import me.pljr.lottery.config.Lang;
import me.pljr.lottery.config.Settings;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.lottery.managers.PlayerManager;
import me.pljr.lottery.menus.ConfirmMenu;
import me.pljr.lottery.menus.ListMenu;
import me.pljr.lottery.menus.MainMenu;
import me.pljr.lottery.menus.PlayerMenu;
import me.pljr.pljrapispigot.commands.BukkitCommand;
import me.pljr.pljrapispigot.utils.VaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LotteryCommand extends BukkitCommand {

    private final Settings settings;
    private final PlayerManager playerManager;
    private final GameLotteryManager gameLotteryManager;

    public LotteryCommand(Settings settings, PlayerManager playerManager, GameLotteryManager gameLotteryManager){
        super("lottery", "lottery.use");
        this.settings = settings;
        this.playerManager = playerManager;
        this.gameLotteryManager = gameLotteryManager;
    }

    @Override
    public void onPlayerCommand(Player player, String[] args){
        // /lottery
        if (args.length < 1){
            new MainMenu(player, gameLotteryManager).getGui().open(player);
            return;
        }

        if (args.length == 1){
            // /lottery help
            if (args[0].equalsIgnoreCase("help")){
                if (!checkPerm(player, "lottery.help")) return;
                sendMessage(player, Lang.HELP.get());
                return;
            }

            // /lottery list
            if (args[0].equalsIgnoreCase("list")){
                if (!checkPerm(player, "lottery.list")) return;
                playerManager.getPlayer(player.getUniqueId(), lotteryPlayer -> {
                    new ListMenu(lotteryPlayer, gameLotteryManager).getGui().open(player);
                });
                return;
            }

            // /lottery stats
            if (args[0].equalsIgnoreCase("stats")){
                if (!checkPerm(player, "lottery.stats")) return;
                playerManager.getPlayer(player.getUniqueId(), lotteryPlayer -> {
                    new PlayerMenu(lotteryPlayer).getGui().open(player);
                });
                return;
            }
        }

        else if (args.length == 2){
            // /lottery buy <amount>
            if (args[0].equalsIgnoreCase("buy")){
                if (!checkPerm(player, "lottery.buy")) return;
                if (!checkInt(player, args[1])) return;
                int amount = Integer.parseInt(args[1]);

                playerManager.getPlayer(player.getUniqueId(), lotteryPlayer -> {
                    UUID playerId = player.getUniqueId();
                    int cost = amount * settings.getCost();
                    double pMoney = VaultUtil.getBalance(player);
                    if (pMoney >= cost){
                        if (settings.isConfirmation()){
                            lotteryPlayer.setConfirmBuyAmount(amount);
                            new ConfirmMenu(player, playerManager, gameLotteryManager, amount, cost).getGui().open(player);
                            playerManager.setPlayer(playerId, lotteryPlayer);
                        }else{
                            VaultUtil.withdraw(player, cost);
                            for (int i=0;i<amount;i++){
                                gameLotteryManager.add(player);
                            }
                            sendMessage(player, Lang.BUY_SUCCESS.get().replace("{amount}", args[1]));
                        }
                        return;
                    }
                    sendMessage(player, Lang.BUY_FAILURE.get().replace("{amount}", args[1]));
                });
                return;
            }

            // /lottery stats <player>
            if (args[0].equalsIgnoreCase("stats")){
                if (!checkPerm(player, "lottery.stats.others")) return;
                if (!checkPlayer(player, args[1])) return;
                playerManager.getPlayer(Bukkit.getPlayer(args[1]).getUniqueId(), lotteryPlayer -> {
                    new PlayerMenu(lotteryPlayer).getGui().open(player);
                });
                return;
            }
        }

        sendMessage(player, Lang.HELP.get());
    }
}
