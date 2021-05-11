package com.thoughtwork;

import com.thoughtwork.action.*;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private Player[] players;
    private int pot;
    private Round currentRound;
    private List<Player> winners;

    public Game(Player ...players) {
        this.players = players;
        this.pot = 0;
        this.currentRound = Round.PREFLOP;
        initCurrentRound(0, new LinkedList<>(Arrays.asList(players)), Arrays.stream(players).collect(Collectors.toMap(p -> p, p -> 0)));
    }

    private void initCurrentRound(int bid, Queue<Player> awaitingPlayers, Map<Player, Integer> roundWagers) {
        currentRound.setCurrentBid(bid);
        currentRound.setAwaitingPlayers(awaitingPlayers);
        currentRound.setRoundWagers(roundWagers);
    }

    public int getPot() {
        return pot;
    }

    public int getMinWager() {
        return 1;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public void setWinners(List<Player> winners) {
        this.winners = winners;
    }

    public void execute(Action action) {
        Player activePlayer = currentRound.getAwaitingPlayers().poll();
        action.execute(this, activePlayer);
        nextRound();
    }

    private void nextRound() {
        int lastBid = currentRound.getCurrentBid();
        Queue<Player> lastAwaitingPlayers = currentRound.getAwaitingPlayers();
        Map<Player, Integer> lastRoundWagers = currentRound.getRoundWagers();

        List<Player> activePlayers = Arrays.stream(players).filter(Player::isActive).collect(Collectors.toList());
        if (activePlayers.size() == 1) {
            currentRound = Round.values()[Round.values().length - 1];
            initCurrentRound(lastBid, lastAwaitingPlayers, lastRoundWagers);
            setWinners(Arrays.asList(getActivePlayer()));
        }
        else if (activePlayers.stream().allMatch(Player::isTookAction)  &&
                activePlayers.stream().allMatch(player -> currentRound.getPlayerWager(player) == currentRound.getCurrentBid())) {
            currentRound = Round.values()[currentRound.ordinal() + 1];
            initCurrentRound(lastBid, lastAwaitingPlayers, lastRoundWagers);
        }
    }

    public void putInPot(Player activePlayer) {
        pot += currentRound.getCurrentBid() - currentRound.getPlayerWager(activePlayer);
    }

    public void awaiting(Player activePlayer) {
        currentRound.awaiting(activePlayer);
    }

    public void putToRoundWager(Player activePlayer) {
        currentRound.putToRoundWagers(activePlayer);
    }

    public Player getActivePlayer() {
        return currentRound.getActivePlayer();
    }

    public Set<ActionType> getActivePlayerActions() {
        return currentRound.getActivePlayerActions();
    }

}
