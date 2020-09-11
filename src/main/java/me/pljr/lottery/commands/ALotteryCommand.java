package me.pljr.lottery.commands;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.config.CfgLang;
import me.pljr.lottery.enums.Lang;
import me.pljr.pljrapi.utils.CommandUtil;
import me.pljr.pljrapi.utils.NumberUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ALotteryCommand extends CommandUtil implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!checkPerm(sender, "alottery.use")) return false;

        // /aloterry help
        if (args.length < 1 || args[0].equalsIgnoreCase("help")){
            sendHelp(sender, CfgLang.adminHelp);
            return false;
        }

        // /alottery restart
        if (args[0].equalsIgnoreCase("restart")){
            if (!checkPerm(sender, "alottery.restart")) return false;
            sendMessage(sender, CfgLang.lang.get(Lang.RESTART_SUCCESS));
            Lottery.getGameLotteryManager().end(false);
            return true;
        }

        // /alottery draw
        if (args[0].equalsIgnoreCase("draw")){
            if (!checkPerm(sender, "alottery.draw")) return false;
            sendMessage(sender, CfgLang.lang.get(Lang.RESTART_SUCCESS));
            Lottery.getGameLotteryManager().end(true);
            return true;
        }

        // /alottery add <int>
        if (args[0].equalsIgnoreCase("add")){
            if (args.length != 2 && !NumberUtil.isInt(args[1])){
                sendHelp(sender, CfgLang.adminHelp);
                return false;
            }
            int amount = Integer.parseInt(args[1]);
            Lottery.getGameLotteryManager().addMoney(amount);
            sendMessage(sender, CfgLang.lang.get(Lang.ADD_MONEY_SUCCESS).replace("%amount", amount+""));
            return true;
        }
        sendHelp(sender, CfgLang.adminHelp);
        return false;
    }
}
