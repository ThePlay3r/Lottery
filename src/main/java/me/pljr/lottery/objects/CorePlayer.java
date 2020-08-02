package me.pljr.lottery.objects;

public class CorePlayer {
    private int currentTickets;
    private int wonAmountTotal;
    private int wonAmountLast;
    private int wonAmountMax;
    private int confirmBuyAmount;

    public CorePlayer(int wonAmountTotal, int wonAmountLast, int wonAmountMax){
        this.currentTickets = 0;
        this.wonAmountTotal = wonAmountTotal;
        this.wonAmountLast = wonAmountLast;
        this.wonAmountMax = wonAmountMax;
        this.confirmBuyAmount = 0;
    }

    public int getCurrentTickets() {
        return currentTickets;
    }
    public void setCurrentTickets(int currentTickets) {
        this.currentTickets = currentTickets;
    }

    public int getWonAmountTotal() {
        return wonAmountTotal;
    }
    public void setWonAmountTotal(int wonAmountTotal) {
        this.wonAmountTotal = wonAmountTotal;
    }

    public int getWonAmountLast() {
        return wonAmountLast;
    }
    public void setWonAmountLast(int wonAmountLast) {
        this.wonAmountLast = wonAmountLast;
    }

    public int getWonAmountMax() {
        return wonAmountMax;
    }
    public void setWonAmountMax(int wonAmountMax) {
        this.wonAmountMax = wonAmountMax;
    }

    public int getConfirmBuyAmount() {
        return confirmBuyAmount;
    }
    public void setConfirmBuyAmount(int confirmBuyAmount) {
        this.confirmBuyAmount = confirmBuyAmount;
    }
}
