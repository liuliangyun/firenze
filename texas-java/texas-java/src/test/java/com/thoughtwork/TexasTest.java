package com.thoughtwork;

import com.thoughtwork.model.Action;
import com.thoughtwork.model.Game;
import com.thoughtwork.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TexasTest {
    @Test
    public void shouldBettingPoolIncreaseWhenPlayerBet() {
        // Given
        Player playerA = new Player("A", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA));
        Game game = new Game(playerList);

        // When
        playerA.bet(game, 10);

        // Then
        assertEquals(90, playerA.getCurrentCounter());
        assertEquals(10, playerA.getCurrentBet());
        assertEquals(10, game.getBettingPot());
    }

    @Test
    public void shouldPlayerBBetWhenPlayerAComplete() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentPlayer(playerA);
        List<Player> waitingPlayers = new ArrayList<>();
        waitingPlayers.addAll(Arrays.asList(playerB, playerC, playerD));
        game.setWaitingPlayers(waitingPlayers);

        // When
        playerA.call(game);

        // Then
        assertEquals(playerB, game.getCurrentPlayer());
        assertThat(game.getWaitingPlayers().containsAll(Arrays.asList(playerC, playerD)), is(true));
    }

    @Test
    public void shouldPlayerCBetWhenPlayerBComplete() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentPlayer(playerB);
        List<Player> waitingPlayers = new ArrayList<>();
        waitingPlayers.addAll(Arrays.asList(playerC, playerD));
        game.setWaitingPlayers(waitingPlayers);

        // When
        playerB.call(game);

        // Then
        assertEquals(playerC, game.getCurrentPlayer());
        assertThat(game.getWaitingPlayers().containsAll(Arrays.asList(playerD)), is(true));
    }

    @Test
    public void shouldPlayerDBetWhenPlayerCComplete() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentPlayer(playerC);
        List<Player> waitingPlayers = new ArrayList<>();
        waitingPlayers.addAll(Arrays.asList(playerD));
        game.setWaitingPlayers(waitingPlayers);

        // When
        playerC.call(game);

        // Then
        assertEquals(playerD, game.getCurrentPlayer());
        assertEquals(0, game.getWaitingPlayers().size());
    }

    @Test
    public void shouldOtherPlayerBetAgainWhenRaise() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentPlayer(playerD);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);

        // When
        playerD.raise(game, 20);

        // Then
        assertEquals(playerA, game.getCurrentPlayer());
        assertThat(game.getWaitingPlayers().containsAll(Arrays.asList(playerB, playerC)), is(true));
    }

    @Test
    public void shouldGoToNextRoundWhenLastPlayerCall() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(0);
        game.setCurrentPlayer(playerD);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);

        // When
        playerD.call(game);

        // Then
        assertEquals(1, game.getCurrentRoundIdx());
    }

    @Test
    public void shouldGoToNextRoundWhenLastPlayerAllIn() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(0);
        game.setCurrentPlayer(playerD);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);

        // When
        playerD.allIn(game);

        // Then
        assertEquals(1, game.getCurrentRoundIdx());
    }

    @Test
    public void shouldGoToNextRoundWhenLastPlayerFold() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(0);
        game.setCurrentPlayer(playerD);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);

        // When
        playerD.fold(game);

        // Then
        assertEquals(1, game.getCurrentRoundIdx());
    }

    @Test
    public void shouldGoToNextRoundWhenLastPlayerCheck() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(0);
        game.setCurrentPlayer(playerD);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);

        // When
        playerD.check(game);

        // Then
        assertEquals(1, game.getCurrentRoundIdx());
    }

    @Test
    public void shouldNotGoToNextRoundWhenLastPlayerRaise() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(0);
        game.setCurrentPlayer(playerD);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);

        // When
        playerD.raise(game, 20);

        // Then
        assertEquals(0, game.getCurrentRoundIdx());
    }

    @Test
    public void shouldCanCheckWhenPlayerHasBiggestBet() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        playerA.bet(game,20);
        game.setBiggestBet(20);

        // When
        game.setCurrentPlayer(playerA);

        // Then
        assertThat(game.getOptionalActions().contains(Action.CHECK), is(true));
    }

    @Test
    public void shouldCanAllInWhenPlayerNotHasEnoughCounter() {
        // Given
        Player playerA = new Player("A", 20);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        playerA.bet(game,10);
        game.setBiggestBet(30);

        // When
        game.setCurrentPlayer(playerA);

        // Then
        assertThat(game.getOptionalActions().contains(Action.ALLIN), is(true));
    }

    @Test
    public void shouldCanNotAllInWhenPlayerHasExactEnoughCounter() {
        // Given
        Player playerA = new Player("A", 30);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        playerA.bet(game, 10);
        game.setBiggestBet(30);

        // When
        game.setCurrentPlayer(playerA);

        // Then
        assertThat(game.getOptionalActions().contains(Action.ALLIN), is(false));
    }

    @Test
    public void shouldCanNotAllInWhenPlayerHasMoreCounter() {
        // Given
        Player playerA = new Player("A", 40);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        playerA.bet(game, 10);
        game.setBiggestBet(30);

        // When
        game.setCurrentPlayer(playerA);

        // Then
        assertThat(game.getOptionalActions().contains(Action.ALLIN), is(false));
    }

    @Test
    public void shouldStillInGamingWhenPlayerAllIn() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        List<Player> allInPlayers = new ArrayList<>();
        game.setAllInPlayers(allInPlayers);
        game.setGamingPlayers(playerList);
        game.setCurrentPlayer(playerA);

        // When
        playerA.allIn(game);

        // Then
        assertThat(game.getAllInPlayers().contains(playerA), is(true));
        assertThat(game.getGamingPlayers().containsAll(Arrays.asList(playerA, playerB, playerC, playerD)), is(true));
    }

    @Test
    public void shouldCanOnlyCheckWhenPlayerIsAlreadyAllIn() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        List<Player> allInPlayers = new ArrayList<>();
        allInPlayers.addAll(Arrays.asList(playerA));
        game.setAllInPlayers(allInPlayers);

        // When
        game.setCurrentPlayer(playerA);

        // Then
        assertEquals(1, game.getOptionalActions().size());
        assertThat(game.getOptionalActions().contains(Action.CHECK), is(true));
    }

    @Test
    public void shouldExitGameWhenPlayerFold() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setGamingPlayers(playerList);
        game.setCurrentPlayer(playerA);

        // When
        playerA.fold(game);

        // Then
        assertEquals(3, game.getGamingPlayers().size());
        assertThat(game.getGamingPlayers().containsAll(Arrays.asList(playerB, playerC, playerD)), is(true));
    }

    @Test
    public void shouldGoToClearRoundWhenOnlyHaveOneGamingPlayer() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(0);
        List<Player> gamingPlayers = new ArrayList<>();
        gamingPlayers.addAll(Arrays.asList(playerC, playerD));
        game.setGamingPlayers(gamingPlayers);
        game.setCurrentPlayer(playerC);

        // When
        playerC.fold(game);

        // Then
        assertEquals(4, game.getCurrentRoundIdx());
        assertEquals(1, game.getGamingPlayers().size());
        assertThat(game.getGamingPlayers().contains(playerD), is(true));
    }

    @Test
    public void shouldGoToClearRoundWhenLastPlayerCallInRoundFour() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(3);
        game.setCurrentPlayer(playerA);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);
        game.setGamingPlayers(playerList);

        // When
        playerA.call(game);

        // Then
        assertEquals(4, game.getCurrentRoundIdx());
        assertThat(game.getGamingPlayers().containsAll(Arrays.asList(playerA, playerB, playerC, playerD)), is(true));
    }

    @Test
    public void shouldGoToClearRoundWhenLastPlayerFoldInRoundFour() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(3);
        game.setCurrentPlayer(playerA);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);
        game.setGamingPlayers(playerList);

        // When
        playerA.fold(game);

        // Then
        assertEquals(4, game.getCurrentRoundIdx());
        assertEquals(3, game.getGamingPlayers().size());
        assertThat(game.getGamingPlayers().containsAll(Arrays.asList(playerB, playerC, playerD)), is(true));
    }

    @Test
    public void shouldGoToClearRoundWhenLastPlayerCheckInRoundFour() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(3);
        game.setCurrentPlayer(playerA);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);
        game.setGamingPlayers(playerList);

        // When
        playerA.check(game);

        // Then
        assertEquals(4, game.getCurrentRoundIdx());
        assertThat(game.getGamingPlayers().containsAll(Arrays.asList(playerA, playerB, playerC, playerD)), is(true));
    }

    @Test
    public void shouldGoToClearRoundWhenLastPlayerALLINInRoundFour() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(3);
        game.setCurrentPlayer(playerA);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);
        game.setGamingPlayers(playerList);

        // When
        playerA.allIn(game);

        // Then
        assertEquals(4, game.getCurrentRoundIdx());
        assertThat(game.getGamingPlayers().containsAll(Arrays.asList(playerA, playerB, playerC, playerD)), is(true));
    }

    @Test
    public void shouldStillInRoundFourWhenLastPlayerRaiseInRoundFour() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setCurrentRoundIdx(3);
        game.setCurrentPlayer(playerA);
        List<Player> waitingPlayers = new ArrayList<>();
        game.setWaitingPlayers(waitingPlayers);
        game.setGamingPlayers(playerList);

        // When
        playerA.raise(game, 20);

        // Then
        assertEquals(3, game.getCurrentRoundIdx());
        assertThat(game.getGamingPlayers().containsAll(Arrays.asList(playerA, playerB, playerC, playerD)), is(true));
    }

    @Test
    public void shouldWinWhenOnlyOnePlayerInClearRound() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        List<Player> gamingPlayers = new ArrayList<>();
        gamingPlayers.add(playerA);
        game.setGamingPlayers(gamingPlayers);
        playerA.setCurrentCounter(80);
        game.setBettingPot(40);

        // When
        game.goToClearRound();

        // Then
        assertEquals(playerA, game.getWinners().get(0));
        assertEquals(1, game.getWinners().size());
        assertEquals(120, playerA.getCurrentCounter());
    }

    @Test
    public void shouldWinAllWhenOnlyOnePlayerWinInClearRound() {
        // Given
        Player playerA = new Player("A", 100);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setGamingPlayers(playerList);
        playerA.setCurrentCounter(80);
        game.setBettingPot(80);

        // When
        List<Player> winners = new ArrayList<>();
        winners.addAll(Arrays.asList(playerA));
        game.setWinners(winners);

        // Then
        assertEquals(160, playerA.getCurrentCounter());
    }

    @Test
    public void shouldDivideBettingPotWhenHasMultipleWinnerInClearRound() {
        // Given
        Player playerA = new Player("A", 150);
        Player playerB = new Player("B", 100);
        Player playerC = new Player("C", 100);
        Player playerD = new Player("D", 100);
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
        Game game = new Game(playerList);
        game.setGamingPlayers(playerList);
        playerA.setCurrentCounter(130);
        playerB.setCurrentCounter(80);
        game.setBettingPot(80);

        // When
        List<Player> winners = new ArrayList<>();
        winners.addAll(Arrays.asList(playerA, playerB));
        game.setWinners(winners);

        // Then
        assertEquals(170, playerA.getCurrentCounter());
        assertEquals(120, playerB.getCurrentCounter());
    }
}
