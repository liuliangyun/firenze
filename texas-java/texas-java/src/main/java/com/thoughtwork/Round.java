package com.thoughtwork;

import java.util.Map;
import java.util.Queue;

public enum Round {
    PREFLOP,
    FLOP,
    TURN,
    RIVER,
    SHOWDOWN;

    private int currentBid;
    private Queue<Player> awaitingPlayers;
    private Map<Player, Integer> roundWagers;


    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public Queue<Player> getAwaitingPlayers() {
        return awaitingPlayers;
    }

    public void setAwaitingPlayers(Queue<Player> awaitingPlayers) {
        this.awaitingPlayers = awaitingPlayers;
    }

    public int getPlayerWager(Player player) {
        return roundWagers.get(player);
    }

    public Map<Player, Integer> getRoundWagers() {
        return roundWagers;
    }

    public void setRoundWagers(Map<Player, Integer> roundWagers) {
        this.roundWagers = roundWagers;
    }

    public void putToRoundWagers (Player activePlayer) {
        roundWagers.put(activePlayer, currentBid);
    }

    public void awaiting(Player activePlayer) {
        awaitingPlayers.offer(activePlayer);
    }

    public Player getActivePlayer() {
        return awaitingPlayers.peek();
    }
}
