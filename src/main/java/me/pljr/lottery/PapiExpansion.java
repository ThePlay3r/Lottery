package me.pljr.lottery;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.pljr.lottery.managers.GameLotteryManager;
import me.pljr.lottery.managers.PlayerManager;
import me.pljr.lottery.objects.GameLottery;
import me.pljr.lottery.objects.LotteryPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@AllArgsConstructor
public class PapiExpansion extends PlaceholderExpansion {

    private final Lottery plugin;
    private final PlayerManager playerManager;
    private final GameLotteryManager gameLotteryManager;

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist(){
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "lottery";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.OfflinePlayer Player}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */
    @Override
    public String onPlaceholderRequest(Player player, String identifier){
        if(player == null){
            return "";
        }

        UUID playerId = player.getUniqueId();

        LotteryPlayer lotteryPlayer = playerManager.getPlayer(playerId);
        GameLottery gameLottery = gameLotteryManager.getCurrentLottery();

        if (identifier.equals("player_current_tickets")){
            return lotteryPlayer.getCurrentTickets()+"";
        }

        if (identifier.equals("player_won_amount_total")){
            return lotteryPlayer.getWonAmountTotal()+"";
        }

        if (identifier.equals("player_won_amount_last")){
            return lotteryPlayer.getWonAmountLast()+"";
        }

        if (identifier.equals("player_won_amount_max")){
            return lotteryPlayer.getWonAmountMax()+"";
        }

        if (identifier.equals("current_time")){
            return gameLotteryManager.getTime()+"";
        }

        if (identifier.equals("current_amount")){
            return gameLottery.getAmount()+"";
        }

        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%)
        // was provided
        return null;
    }
}
