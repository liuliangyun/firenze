//package com.thoughtwork;
//
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.*;
//import org.assertj.core.api.Assertions;
//
//public class TexasTest {
//    @Test
//    public void shouldCanCheckWhenPlayerHasBiggestBet() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        playerA.bet(game,20);
//        game.setBiggestBet(20);
//
//        // When
//        game.setCurrentPlayer(playerA);
//
//        // Then
//        assertTrue(game.getOptionalActions().contains(Action.CHECK));
//    }
//
//    @Test
//    public void shouldCanAllInWhenPlayerNotHasEnoughCounter() {
//        // Given
//        Player playerA = new Player("A", 20);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        playerA.bet(game,10);
//        game.setBiggestBet(30);
//
//        // When
//        game.setCurrentPlayer(playerA);
//
//        // Then
//        assertTrue(game.getOptionalActions().contains(Action.ALLIN));
//    }
//
//    @Test
//    public void shouldCanNotAllInWhenPlayerHasExactEnoughCounter() {
//        // Given
//        Player playerA = new Player("A", 30);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        playerA.bet(game, 10);
//        game.setBiggestBet(30);
//
//        // When
//        game.setCurrentPlayer(playerA);
//
//        // Then
//        assertFalse(game.getOptionalActions().contains(Action.ALLIN));
//    }
//
//    @Test
//    public void shouldCanNotAllInWhenPlayerHasMoreCounter() {
//        // Given
//        Player playerA = new Player("A", 40);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        playerA.bet(game, 10);
//        game.setBiggestBet(30);
//
//        // When
//        game.setCurrentPlayer(playerA);
//
//        // Then
//        assertFalse(game.getOptionalActions().contains(Action.ALLIN));
//    }
//
//    @Test
//    public void shouldStillInGamingWhenPlayerAllIn() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        List<Player> allInPlayers = new ArrayList<>();
//        game.setAllInPlayers(allInPlayers);
//        game.setGamingPlayers(playerList);
//        game.setCurrentPlayer(playerA);
//
//        // When
//        playerA.allIn(game);
//
//        // Then
//        Assertions.assertThat(game.getAllInPlayers()).containsExactly(playerA);
//        Assertions.assertThat(game.getGamingPlayers()).containsExactly(playerA, playerB, playerC, playerD);
//    }
//
//    @Test
//    public void shouldCanOnlyCheckWhenPlayerIsAlreadyAllIn() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        List<Player> allInPlayers = new ArrayList<>();
//        allInPlayers.addAll(Arrays.asList(playerA));
//        game.setAllInPlayers(allInPlayers);
//
//        // When
//        game.setCurrentPlayer(playerA);
//
//        // Then
//        Assertions.assertThat(game.getOptionalActions()).containsExactly(Action.CHECK);
//    }
//
//    @Test
//    public void shouldExitGameWhenPlayerFold() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        game.setGamingPlayers(playerList);
//        game.setCurrentPlayer(playerA);
//
//        // When
//        playerA.fold(game);
//
//        // Then
//        Assertions.assertThat(game.getGamingPlayers()).containsExactly(playerB, playerC, playerD);
//    }
//
//    @Test
//    public void shouldGoToClearRoundWhenOnlyHaveOneGamingPlayer() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        game.setCurrentRoundIdx(0);
//        List<Player> gamingPlayers = new ArrayList<>();
//        gamingPlayers.addAll(Arrays.asList(playerC, playerD));
//        game.setGamingPlayers(gamingPlayers);
//        game.setCurrentPlayer(playerC);
//
//        // When
//        playerC.fold(game);
//
//        // Then
//        assertEquals(4, game.getCurrentRoundIdx());
//        Assertions.assertThat(game.getGamingPlayers()).containsExactly(playerD);
//    }
//
//    @Test
//    public void shouldGoToClearRoundWhenLastPlayerCallInRoundFour() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        game.setCurrentRoundIdx(3);
//        game.setCurrentPlayer(playerA);
//        List<Player> waitingPlayers = new ArrayList<>();
//        game.setWaitingPlayers(waitingPlayers);
//        game.setGamingPlayers(playerList);
//
//        // When
//        playerA.call(game);
//
//        // Then
//        assertEquals(4, game.getCurrentRoundIdx());
//        Assertions.assertThat(game.getGamingPlayers()).containsExactly(playerA, playerB, playerC, playerD);
//    }
//
//    @Test
//    public void shouldGoToClearRoundWhenLastPlayerFoldInRoundFour() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        game.setCurrentRoundIdx(3);
//        game.setCurrentPlayer(playerA);
//        List<Player> waitingPlayers = new ArrayList<>();
//        game.setWaitingPlayers(waitingPlayers);
//        game.setGamingPlayers(playerList);
//
//        // When
//        playerA.fold(game);
//
//        // Then
//        assertEquals(4, game.getCurrentRoundIdx());
//        Assertions.assertThat(game.getGamingPlayers()).containsExactly(playerB, playerC, playerD);
//    }
//
//    @Test
//    public void shouldGoToClearRoundWhenLastPlayerCheckInRoundFour() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        game.setCurrentRoundIdx(3);
//        game.setCurrentPlayer(playerA);
//        List<Player> waitingPlayers = new ArrayList<>();
//        game.setWaitingPlayers(waitingPlayers);
//        game.setGamingPlayers(playerList);
//
//        // When
//        playerA.check(game);
//
//        // Then
//        assertEquals(4, game.getCurrentRoundIdx());
//        Assertions.assertThat(game.getGamingPlayers()).containsExactly(playerA, playerB, playerC, playerD);
//    }
//
//    @Test
//    public void shouldGoToClearRoundWhenLastPlayerALLINInRoundFour() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        game.setCurrentRoundIdx(3);
//        game.setCurrentPlayer(playerA);
//        List<Player> waitingPlayers = new ArrayList<>();
//        game.setWaitingPlayers(waitingPlayers);
//        game.setGamingPlayers(playerList);
//
//        // When
//        playerA.allIn(game);
//
//        // Then
//        assertEquals(4, game.getCurrentRoundIdx());
//        Assertions.assertThat(game.getGamingPlayers()).containsExactly(playerA, playerB, playerC, playerD);
//    }
//
//    @Test
//    public void shouldStillInRoundFourWhenLastPlayerRaiseInRoundFour() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        game.setCurrentRoundIdx(3);
//        game.setCurrentPlayer(playerA);
//        List<Player> waitingPlayers = new ArrayList<>();
//        game.setWaitingPlayers(waitingPlayers);
//        game.setGamingPlayers(playerList);
//
//        // When
//        playerA.raise(game, 20);
//
//        // Then
//        assertEquals(3, game.getCurrentRoundIdx());
//        Assertions.assertThat(game.getGamingPlayers()).containsExactly(playerA, playerB, playerC, playerD);
//    }
//
//    @Test
//    public void shouldWinWhenOnlyOnePlayerInClearRound() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        List<Player> gamingPlayers = new ArrayList<>();
//        gamingPlayers.add(playerA);
//        game.setGamingPlayers(gamingPlayers);
//        playerA.setCurrentCounter(80);
//        game.setBettingPot(40);
//
//        // When
//        game.goToClearRound();
//
//        // Then
//        Assertions.assertThat(game.getWinners()).containsExactly(playerA);
//        assertEquals(120, playerA.getCurrentCounter());
//    }
//
//    @Test
//    public void shouldWinAllWhenOnlyOnePlayerWinInClearRound() {
//        // Given
//        Player playerA = new Player("A", 100);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        game.setGamingPlayers(playerList);
//        playerA.setCurrentCounter(80);
//        game.setBettingPot(80);
//
//        // When
//        List<Player> winners = new ArrayList<>();
//        winners.addAll(Arrays.asList(playerA));
//        game.setWinners(winners);
//
//        // Then
//        assertEquals(160, playerA.getCurrentCounter());
//    }
//
//    @Test
//    public void shouldDivideBettingPotWhenHasMultipleWinnerInClearRound() {
//        // Given
//        Player playerA = new Player("A", 150);
//        Player playerB = new Player("B", 100);
//        Player playerC = new Player("C", 100);
//        Player playerD = new Player("D", 100);
//        List<Player> playerList = new ArrayList<>();
//        playerList.addAll(Arrays.asList(playerA, playerB, playerC, playerD));
//        Game game = new Game(playerList);
//        game.setGamingPlayers(playerList);
//        playerA.setCurrentCounter(130);
//        playerB.setCurrentCounter(80);
//        game.setBettingPot(80);
//
//        // When
//        List<Player> winners = new ArrayList<>();
//        winners.addAll(Arrays.asList(playerA, playerB));
//        game.setWinners(winners);
//
//        // Then
//        assertEquals(170, playerA.getCurrentCounter());
//        assertEquals(120, playerB.getCurrentCounter());
//    }
//}
