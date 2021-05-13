package com.thoughtwork;

import com.thoughtwork.action.*;
import org.junit.Test;

import static org.junit.Assert.*;


// 不考虑Allin
public class GameTest {
    @Test
    public void player_a_should_be_the_first_active_player () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));

        assertEquals("A", game.getActivePlayer().getName());
        assertEquals(0, game.getPot());
        assertEquals(0, game.getCurrentRound().getCurrentBid());
    }

    @Test
    public void player_b_should_be_next_player_when_player_a_pass () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Pass());

        assertEquals("B", game.getActivePlayer().getName());
    }

    @Test
    public void player_b_should_be_next_player_when_player_a_bet () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals("B", game.getActivePlayer().getName());
    }

    @Test
    public void should_set_current_bid_to_min_wager_when_player_a_bet () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals(1, game.getCurrentRound().getCurrentBid());
        assertEquals(1, game.getPot());
    }

    @Test
    public void should_enter_next_round_when_all_players_pass () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        assertEquals(Round.PREFLOP, game.getCurrentRound());

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Pass());

        assertEquals("B", game.getActivePlayer().getName());
        game.execute(new Pass());

        assertEquals("C", game.getActivePlayer().getName());
        game.execute(new Pass());

        assertEquals("D", game.getActivePlayer().getName());
        game.execute(new Pass());

        assertEquals(Round.FLOP, game.getCurrentRound());
    }

    @Test
    public void should_enter_next_round_when_all_players_bet () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        assertEquals(Round.PREFLOP, game.getCurrentRound());

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals("B", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals("C", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals("D", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals(Round.FLOP, game.getCurrentRound());
    }

    @Test
    public void should_not_enter_enter_next_round_when_player_d_raise () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        assertEquals(Round.PREFLOP, game.getCurrentRound());

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals("B", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals("C", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals("D", game.getActivePlayer().getName());
        game.execute(new Raise(2));

        assertEquals(Round.PREFLOP, game.getCurrentRound());
        assertEquals("A", game.getActivePlayer().getName());
    }

    @Test
    public void should_bet_same_wager_after_someone_raise_the_wager () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        assertEquals(Round.PREFLOP, game.getCurrentRound());

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Bet());
        int playerAWager = game.getCurrentRound().getCurrentBid();
        assertEquals(1, playerAWager);

        assertEquals("B", game.getActivePlayer().getName());
        game.execute(new Bet());
        int playerBWager = game.getCurrentRound().getCurrentBid();
        assertEquals(1, playerBWager);

        assertEquals("C", game.getActivePlayer().getName());
        game.execute(new Bet());
        int playerCWager = game.getCurrentRound().getCurrentBid();
        assertEquals(1, playerCWager);

        assertEquals("D", game.getActivePlayer().getName());
        game.execute(new Raise(2));

        assertEquals(2, game.getCurrentRound().getCurrentBid());
        int potBeforeABet = game.getPot();

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals(2, game.getCurrentRound().getCurrentBid());
        assertEquals(game.getPot(), potBeforeABet + game.getCurrentRound().getCurrentBid() - playerAWager);
        int potBeforeBBet = game.getPot();

        assertEquals("B", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals(2, game.getCurrentRound().getCurrentBid());
        assertEquals(game.getPot(), potBeforeBBet + game.getCurrentRound().getCurrentBid() - playerBWager);
        int potBeforeCBet = game.getPot();

        assertEquals("C", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals(Round.FLOP, game.getCurrentRound());
        assertEquals(game.getPot(), potBeforeCBet + game.getCurrentRound().getCurrentBid() - playerCWager);
    }

    @Test
    public void should_player_a_exit_game_when_player_a_fold () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        assertEquals(Round.PREFLOP, game.getCurrentRound());

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Fold());

        assertEquals("B", game.getActivePlayer().getName());
        game.execute(new Pass());

        assertEquals("C", game.getActivePlayer().getName());
        game.execute(new Pass());

        assertEquals("D", game.getActivePlayer().getName());
        game.execute(new Pass());

        assertEquals(Round.FLOP, game.getCurrentRound());
        assertEquals("B", game.getActivePlayer().getName());
    }

    @Test
    public void should_can_pass_when_player_wager_equal_to_current_bid () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        assertEquals(Round.PREFLOP, game.getCurrentRound());

        assertEquals("A", game.getActivePlayer().getName());
        assertTrue(game.getActivePlayerActions().contains(ActionType.PASS));
        game.execute(new Bet());

        assertEquals("B", game.getActivePlayer().getName());
        assertFalse(game.getActivePlayerActions().contains(ActionType.PASS));
    }

    @Test
    public void should_can_not_bet_when_player_wager_equal_to_current_bid_and_not_empty() {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        assertEquals(Round.PREFLOP, game.getCurrentRound());
        assertEquals(0, game.getCurrentRound().getCurrentBid());
        assertEquals("A", game.getActivePlayer().getName());
        assertTrue(game.getActivePlayerActions().contains(ActionType.BET));

        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());

        assertEquals(Round.FLOP, game.getCurrentRound());
        assertEquals(1, game.getCurrentRound().getCurrentBid());
        assertEquals("A", game.getActivePlayer().getName());
        assertFalse(game.getActivePlayerActions().contains(ActionType.BET));
    }

    @Test
    public void should_enter_final_round_when_only_one_active_player () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        assertEquals(Round.PREFLOP, game.getCurrentRound());

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Fold());

        assertEquals("B", game.getActivePlayer().getName());
        game.execute(new Fold());

        assertEquals("C", game.getActivePlayer().getName());
        game.execute(new Fold());

        assertEquals(Round.SHOWDOWN, game.getCurrentRound());
    }

    @Test
    public void should_player_win_when_player_is_the_only_one_active_player () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        assertEquals(Round.PREFLOP, game.getCurrentRound());

        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals("B", game.getActivePlayer().getName());
        game.execute(new Fold());

        assertEquals("C", game.getActivePlayer().getName());
        game.execute(new Fold());

        assertEquals("D", game.getActivePlayer().getName());
        game.execute(new Bet());

        assertEquals(Round.FLOP, game.getCurrentRound());
        assertEquals("A", game.getActivePlayer().getName());
        game.execute(new Fold());

        assertEquals(Round.SHOWDOWN, game.getCurrentRound());
        assertTrue(game.getActivePlayer().isWin());
        assertEquals(game.getPot(), game.getActivePlayer().getAward());
    }

    @Test
    public void should_game_poker_has_52_cards () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));

        assertEquals(52, game.getPoker().getCardList().size());
    }

    @Test
    public void should_deal_2_cards_for_each_player_when_game_start () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));

        assertEquals(2, game.getPlayers()[0].getCards().size());
        assertEquals(2, game.getPlayers()[1].getCards().size());
        assertEquals(2, game.getPlayers()[2].getCards().size());
        assertEquals(2, game.getPlayers()[3].getCards().size());
    }

    @Test
    public void should_deal_public_cards_when_enter_some_round () {
        Game game = new Game(new Player("A"), new Player("B"), new Player("C"), new Player("D"));
        assertEquals(Round.PREFLOP, game.getCurrentRound());
        assertEquals(0, game.getPoker().getPublicCards().size());

        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());

        assertEquals(Round.FLOP, game.getCurrentRound());
        assertEquals(3, game.getPoker().getPublicCards().size());

        game.execute(new Raise(2));
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());

        assertEquals(Round.TURN, game.getCurrentRound());
        assertEquals(4, game.getPoker().getPublicCards().size());

        game.execute(new Raise(3));
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());

        assertEquals(Round.RIVER, game.getCurrentRound());
        assertEquals(5, game.getPoker().getPublicCards().size());

        game.execute(new Raise(4));
        game.execute(new Bet());
        game.execute(new Bet());
        game.execute(new Bet());

        assertEquals(Round.SHOWDOWN, game.getCurrentRound());
        assertEquals(5, game.getPoker().getPublicCards().size());
    }


    @Test
    public void should_win_all_pot_when_only_one_winner () {

    }

    @Test
    public void should_divide_pot_when_has_multiple_winners () {

    }
}
