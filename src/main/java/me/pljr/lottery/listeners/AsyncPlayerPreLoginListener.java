package me.pljr.lottery.listeners;

import lombok.AllArgsConstructor;
import me.pljr.lottery.Lottery;
import me.pljr.lottery.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

@AllArgsConstructor
public class AsyncPlayerPreLoginListener implements Listener {
    private final PlayerManager playerManager;

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event){
        playerManager.getPlayer(event.getUniqueId(), ignored -> Lottery.log.info("Loaded " + event.getName()));
    }
}
