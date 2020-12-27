package me.pljr.lottery.commands;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.Lang;
import me.pljr.pljrapispigot.utils.CommandUtil;
import me.pljr.pljrapispigot.utils.NumberUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ALotteryCommand extends CommandUtil {

    public ALotteryCommand(){
        super("alottery", "alottery.use");
    }

    @Override
    public void onPlayerCommand(Player player, String[] args){
        if (args.length == 1){
            // /aloterry help
            if (args.length < 1 || args[0].equalsIgnoreCase("help")){
                sendMessage(player, Lang.ADMIN_HELP);
                return;
            }

            // /alottery restart
            if (args[0].equalsIgnoreCase("restart")){
                if (!checkPerm(player, "alottery.restart")) return;
                sendMessage(player, Lang.RESTART_SUCCESS.get());
                Lottery.getGameLotteryManager().end(false);
                return;
            }

            // /alottery draw
            if (args[0].equalsIgnoreCase("draw")){
                if (!checkPerm(player, "alottery.draw")) return;
                sendMessage(player, Lang.RESTART_SUCCESS.get());
                Lottery.getGameLotteryManager().end(true);
                return;
            }
        }

        else if (args.length == 2){
            // /alottery add <int>
            if (args[0].equalsIgnoreCase("add")){
                if (!checkInt(player, args[1])) return;
                int amount = Integer.parseInt(args[1]);
                Lottery.getGameLotteryManager().addMoney(amount);
                sendMessage(player, Lang.ADD_MONEY_SUCCESS.get().replace("{amount}", amount+""));
                return;
            }
        }
        sendMessage(player, Lang.ADMIN_HELP);
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender sender, String[] args){
        if (args.length == 1){
            // /aloterry help
            if (args.length < 1 || args[0].equalsIgnoreCase("help")){
                sendMessage(sender, Lang.ADMIN_HELP);
                return;
            }

            // /alottery restart
            if (args[0].equalsIgnoreCase("restart")){
                sendMessage(sender, Lang.RESTART_SUCCESS.get());
                Lottery.getGameLotteryManager().end(false);
                return;
            }

            // /alottery draw
            if (args[0].equalsIgnoreCase("draw")){
                sendMessage(sender, Lang.RESTART_SUCCESS.get());
                Lottery.getGameLotteryManager().end(true);
                return;
            }
        }

        else if (args.length == 2){
            // /alottery add <int>
            if (args[0].equalsIgnoreCase("add")){
                if (!checkInt(sender, args[1])) return;
                int amount = Integer.parseInt(args[1]);
                Lottery.getGameLotteryManager().addMoney(amount);
                sendMessage(sender, Lang.ADD_MONEY_SUCCESS.get().replace("{amount}", amount+""));
                return;
            }
        }

        sendMessage(sender, Lang.ADMIN_HELP);
    }
}
