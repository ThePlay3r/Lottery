package me.pljr.lottery.objects;

import java.util.ArrayList;
import java.util.List;

public class GameLottery {
    private int amount;
    private List<String> players;

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

    public void setPlayers(List<String> players) {
        this.players = players;
    }
    public List<String> getPlayers() {
        return players;
    }
}
