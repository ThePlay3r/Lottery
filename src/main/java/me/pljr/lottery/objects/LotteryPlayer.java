package me.pljr.lottery.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class LotteryPlayer {
    private final UUID uniqueId;
    private int currentTickets;
    private int wonAmountTotal;
    private int wonAmountLast;
    private int wonAmountMax;
    private int confirmBuyAmount;

    public LotteryPlayer(UUID uniqueId){
        this.uniqueId = uniqueId;
        this.currentTickets = 0;
        this.wonAmountTotal = 0;
        this.wonAmountLast = 0;
        this.wonAmountMax = 0;
        this.confirmBuyAmount = 0;
    }
}
