package com.thoughtwork.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    // 游戏总轮次
    public static final Round GAME_PROCESS[] = {Round.ONE, Round.TWO, Round.THREE, Round.FOUR, Round.CLEAR};
    // 游戏玩家列表
    private List<Player> playerList;
    // 游戏当前轮次
    private int currentRoundIdx;
    // 游戏当前操作玩家
    private Player currentPlayer;
    // 游戏等待操作玩家
    private List<Player> waitingPlayers;
    // 游戏中的玩家
    private List<Player> gamingPlayers;
    // 游戏All in玩家
    private List<Player> allInPlayers;
    // 游戏最高投注金额
    private int biggestBet;
    // 底池金额
    private int bettingPot;
    // 赢家
    private List<Player> winners;

    public Game(List<Player> playerList) {
        this.playerList = playerList;
        this.currentRoundIdx = 0;
        this.currentPlayer = playerList.get(0);
        this.gamingPlayers = playerList;
        this.waitingPlayers = new ArrayList<>();
        this.allInPlayers = new ArrayList<>();
        this.biggestBet = 0;
        this.bettingPot = 0;
        this.winners = new ArrayList<>();
    }

    // 游戏进入下一轮
    public void goToNextRound() {
        currentRoundIdx += 1;
        List<Player> newWaitingPlayers = new ArrayList<>();
        newWaitingPlayers.addAll(gamingPlayers);
        currentPlayer = newWaitingPlayers.get(0);
        newWaitingPlayers.remove(0);
        waitingPlayers = newWaitingPlayers;
    }

    // 游戏进入结算阶段
    public void goToClearRound() {
        currentRoundIdx = 4;
        currentPlayer = null;
        waitingPlayers.clear();

        if (gamingPlayers.size() == 1) {
            setWinners(gamingPlayers);
        }
    }

    // 轮到下一个玩家操作
    public void goToNextPlayer() {
        List<Player> newWaitingPlayers = new ArrayList<>();
        newWaitingPlayers.addAll(waitingPlayers);
        currentPlayer = newWaitingPlayers.get(0);
        newWaitingPlayers.remove(0);
        waitingPlayers = newWaitingPlayers;
    }

    // 获取游戏当前可进行的操作
    public List<Action> getOptionalActions() {
        List<Action> optionalActions = new ArrayList<>();
        if (currentPlayer.getCurrentBet() == biggestBet) {
            optionalActions.add(Action.CHECK);
        } else if (currentPlayer.getCurrentBet() < biggestBet) {
            if (currentPlayer.getCurrentCounter() < biggestBet - currentPlayer.getCurrentBet()) {
                optionalActions.add(Action.ALLIN);
            }
        }
        return optionalActions;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Player> getWaitingPlayers() {
        return waitingPlayers;
    }

    public void setWaitingPlayers(List<Player> waitingPlayers) {
        this.waitingPlayers = waitingPlayers;
    }

    public List<Player> getGamingPlayers() {
        return gamingPlayers;
    }

    public void setGamingPlayers(List<Player> gamingPlayers) {
        this.gamingPlayers = gamingPlayers;
    }

    public void setCurrentRoundIdx(int currentRoundIdx) {
        this.currentRoundIdx = currentRoundIdx;
    }

    public int getCurrentRoundIdx() {
        return currentRoundIdx;
    }

    public List<Player> getAllInPlayers() {
        return allInPlayers;
    }

    public void setAllInPlayers(List<Player> allInPlayers) {
        this.allInPlayers = allInPlayers;
    }

    public int getBiggestBet() {
        return biggestBet;
    }

    public void setBiggestBet(int biggestBet) {
        this.biggestBet = biggestBet;
    }

    public int getBettingPot() {
        return bettingPot;
    }

    public void setBettingPot(int bettingPot) {
        this.bettingPot = bettingPot;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public void setWinners(List<Player> winners) {
        this.winners = winners;
        winners
            .stream()
            .forEach(winner -> winner.setCurrentCounter(winner.getCurrentCounter() + bettingPot / winners.size()));
    }
}
