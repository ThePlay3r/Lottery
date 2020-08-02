package me.pljr.lottery.managers;

import me.pljr.lottery.Lottery;
import me.pljr.lottery.objects.CorePlayer;

import java.util.HashMap;

public class PlayerManager {
    private static final HashMap<String, CorePlayer> players = new HashMap<>();
    private static final QueryManager query = Lottery.getQueryManager();
    private static final HashMap<String, String> requests = new HashMap<>();

    public static CorePlayer getCorePlayer(String pName){
        if (players.containsKey(pName)){
            return players.get(pName);
        }
        query.loadPlayerSync(pName);
        return getCorePlayer(pName);
    }

    public static void setCorePlayer(String pName, CorePlayer corePlayer){
        players.put(pName, corePlayer);
    }

    public static void savePlayer(String pName){
        if (!players.containsKey(pName)) return;
        query.savePlayer(pName);
    }

    public static HashMap<String, String> getRequests() {
        return requests;
    }
}
