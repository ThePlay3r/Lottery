package me.pljr.lottery.objects;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameLottery {
    private int amount;
    private List<Player> players;

    public GameLottery(){
        this.amount = 0;
        this.players = new ArrayList<>();
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public List<Player> getPlayers() {
        return players;
    }
}
