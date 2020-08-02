package me.pljr.lottery.listeners;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.managers.QueryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final QueryManager query = Lottery.getQueryManager();

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String playerName = player.getName();
        query.savePlayer(playerName);
    }
}
