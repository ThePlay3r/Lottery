package me.pljr.lottery.commands;

import me.pljr.lottery.config.CfgLang;
import me.pljr.lottery.config.CfgSettings;
import me.pljr.lottery.enums.Lang;
import me.pljr.lottery.menus.ConfirmMenu;
import me.pljr.lottery.menus.ListMenu;
import me.pljr.lottery.menus.MainMenu;
import me.pljr.lottery.menus.PlayerMenu;
import me.pljr.lottery.utils.GameLotteryUtil;
import me.pljr.pljrapi.utils.CommandUtil;
import me.pljr.pljrapi.utils.NumberUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LotteryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(CfgLang.lang.get(Lang.NO_CONSOLE));
            return false;
        }
        Player player = (Player) sender;
        if (!CommandUtil.checkPerm(player, "lottery.use")) return false;

        // /lottery
        if (args.length < 1){
            MainMenu.open(player);
            return true;
        }

        // /lottery help
        if (args[0].equalsIgnoreCase("help")){
            if (!CommandUtil.checkPerm(player, "lottery.help")) return false;
            CommandUtil.sendHelp(player, CfgLang.help);
            return true;
        }

        // /lottery list
        if (args[0].equalsIgnoreCase("list")){
            if (!CommandUtil.checkPerm(player, "lottery.list")) return false;
            ListMenu.open(player);
            return true;
        }

        // /lottery buy <amount>
        if (args[0].equalsIgnoreCase("buy")){
            if (!CommandUtil.checkPerm(player, "lottery.buy")) return false;
            if (args.length != 2){
                CommandUtil.sendHelp(player, CfgLang.help);
                return false;
            }
            if (!NumberUtil.isInt(args[1])){
                player.sendMessage(CfgLang.lang.get(Lang.NO_NUMBER).replace("%arg", args[1]));
            }
            int amount = Integer.parseInt(args[1]);
            if (GameLotteryUtil.buy(player, amount)){
                if (CfgSettings.confirmation){
                    ConfirmMenu.open(player);
                    return true;
                }
                player.sendMessage(CfgLang.lang.get(Lang.BUY_SUCCESS).replace("%amount", args[1]));
                return true;
            }
            player.sendMessage(CfgLang.lang.get(Lang.BUY_FAILURE).replace("%amount", args[1]));
            return false;
        }

        // /lottery stats <player>
        if (args[0].equalsIgnoreCase("stats")){
            if (!CommandUtil.checkPerm(player, "lottery.stats")) return false;
            if (args.length != 2){
                PlayerMenu.open(player, player.getName());
                return true;
            }
            if (!CommandUtil.checkPerm(player, "lottery.stats.others")) return false;
            PlayerMenu.open(player, args[1]);
            return true;
        }

        CommandUtil.sendHelp(player, CfgLang.help);
        return false;
    }
}
