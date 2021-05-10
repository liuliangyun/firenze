package com.thoughtwork.action;

import com.thoughtwork.Game;
import com.thoughtwork.Player;

public class Fold implements Action {
    @Override
    public void execute(Game game, Player activePlayer) {
        activePlayer.setTookAction(true);
        activePlayer.setActive(false);
    }
}
