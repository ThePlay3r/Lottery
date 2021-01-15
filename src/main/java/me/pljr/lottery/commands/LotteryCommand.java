package me.pljr.lottery.commands;

import me.pljr.lottery.config.CfgSettings;
import me.pljr.lottery.config.Lang;
import me.pljr.lottery.menus.ConfirmMenu;
import me.pljr.lottery.menus.ListMenu;
import me.pljr.lottery.menus.MainMenu;
import me.pljr.lottery.menus.PlayerMenu;
import me.pljr.lottery.utils.GameLotteryUtil;
import me.pljr.pljrapispigot.utils.CommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LotteryCommand extends CommandUtil {

    public LotteryCommand(){
        super("lottery", "lottery.use");
    }

    @Override
    public void onPlayerCommand(Player player, String[] args){
        // /lottery
        if (args.length < 1){
            MainMenu.get(player).open(player);
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
                ListMenu.get(player).open(player);
                return;
            }

            // /lottery stats
            if (args[0].equalsIgnoreCase("stats")){
                if (!checkPerm(player, "lottery.stats")) return;
                PlayerMenu.get(player, player).open(player);
                return;
            }
        }

        else if (args.length == 2){
            // /lottery buy <amount>
            if (args[0].equalsIgnoreCase("buy")){
                if (!checkPerm(player, "lottery.buy")) return;
                if (!checkInt(player, args[1])) return;
                int amount = Integer.parseInt(args[1]);
                if (GameLotteryUtil.buy(player, amount)){
                    if (CfgSettings.CONFIRMATION){
                        ConfirmMenu.get(player).open(player);
                        return;
                    }
                    sendMessage(player, Lang.BUY_SUCCESS.get().replace("{amount}", args[1]));
                    return;
                }
                sendMessage(player, Lang.BUY_FAILURE.get().replace("{amount}", args[1]));
                return;
            }

            // /lottery stats <player>
            if (args[0].equalsIgnoreCase("stats")){
                if (!checkPerm(player, "lottery.stats.others")) return;
                if (!checkPlayer(player, args[1])) return;
                PlayerMenu.get(player, Bukkit.getPlayer(args[1])).open(player);
                return;
            }
        }

        sendMessage(player, Lang.HELP.get());
    }
}
