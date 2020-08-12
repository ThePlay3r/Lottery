package me.pljr.lottery.listeners;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.managers.QueryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {
    private final QueryManager query = Lottery.getQueryManager();

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event){
        query.loadPlayerSync(event.getUniqueId());
    }
}
