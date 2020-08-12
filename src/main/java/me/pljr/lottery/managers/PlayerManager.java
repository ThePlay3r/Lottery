package me.pljr.lottery.managers;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.objects.CorePlayer;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private static final HashMap<UUID, CorePlayer> players = new HashMap<>();
    private static final QueryManager query = Lottery.getQueryManager();

    public static CorePlayer getCorePlayer(UUID uuid){
        if (players.containsKey(uuid)){
            return players.get(uuid);
        }
        query.loadPlayerSync(uuid);
        return getCorePlayer(uuid);
    }

    public static void setCorePlayer(UUID uuid, CorePlayer corePlayer){
        players.put(uuid, corePlayer);
    }

    public static void savePlayer(UUID uuid){
        if (!players.containsKey(uuid)) return;
        query.savePlayer(uuid);
    }
}
