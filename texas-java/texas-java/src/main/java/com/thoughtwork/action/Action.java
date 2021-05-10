package com.thoughtwork.action;

import com.thoughtwork.Game;
import com.thoughtwork.Player;

public interface Action {
    public void execute(Game game, Player activePlayer);
}
