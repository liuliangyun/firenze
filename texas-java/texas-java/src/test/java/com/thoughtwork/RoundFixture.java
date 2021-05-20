package com.thoughtwork;

import com.thoughtwork.action.Action;
import com.thoughtwork.action.Bet;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(ConcordionRunner.class)
public class RoundFixture {

    private Game game;
    private Round round;

    public void newGame(String players) {
        game = new Game(Arrays.stream(players.split(",")).map(Player::new).toArray(Player[]::new));
        round = game.getCurrentRound();
    }

    public Game getCurrentGame() {
        return game;
    }

    public void bet() {
        game.execute(new Bet());
    }

    public boolean isPotEqualMinWager() {
        return game.getPot() == game.getMinWager();
    }

    public boolean isEnterNextRound() {
        return round != game.getCurrentRound();
    }

    public String getCurrentActivePlayerName() {
        return game.getActivePlayer().getName();
    }

}
