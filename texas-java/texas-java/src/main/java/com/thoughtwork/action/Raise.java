package com.thoughtwork.action;

import com.thoughtwork.Game;
import com.thoughtwork.Player;

public class Raise implements Action {
    private int wager;

    public Raise(int wager) {
        this.wager = wager;
    }

    @Override
    public void execute(Game game, Player activePlayer) {
        activePlayer.setTookAction(true);
        game.getCurrentRound().setCurrentBid(wager);
        game.putInPot(activePlayer);
        game.putToRoundWager(activePlayer);
        game.awaiting(activePlayer);
    }
}
