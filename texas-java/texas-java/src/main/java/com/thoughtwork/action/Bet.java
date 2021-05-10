package com.thoughtwork.action;

import com.thoughtwork.Game;
import com.thoughtwork.Player;

public class Bet implements Action {
    @Override
    public void execute(Game game, Player activePlayer) {
        activePlayer.setTookAction(true);
        if (game.getCurrentRound().getCurrentBid() == 0) {
            game.getCurrentRound().setCurrentBid(game.getMinWager());
        }
        game.putInPot(activePlayer);
        game.putToRoundWager(activePlayer);
        game.awaiting(activePlayer);
    }
}
