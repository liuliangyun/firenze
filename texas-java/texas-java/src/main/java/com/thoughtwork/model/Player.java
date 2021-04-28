package com.thoughtwork.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    // 玩家的唯一标志
    private String id;
    // 玩家筹码总数
    private int totalCounter;
    // 玩家当前筹码数
    private int currentCounter;
    // 玩家当前投注金额
    private int currentBet;

    public Player(String id, int totalCounter) {
        this.id = id;
        this.totalCounter = totalCounter;
        this.currentCounter = totalCounter;
        this.currentBet = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotalCounter() {
        return totalCounter;
    }

    public void setTotalCounter(int totalCounter) {
        this.totalCounter = totalCounter;
    }

    public int getCurrentCounter() {
        return currentCounter;
    }

    public void setCurrentCounter(int currentCounter) {
        this.currentCounter = currentCounter;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    // 下注
    public void bet(Game game, int amount) {
        game.setBettingPot(game.getBettingPot() + amount);
        currentCounter = currentCounter - amount;
        currentBet = currentBet + amount;
    }

    // 加注
    public void raise(Game game, int amount) {
        bet(game, amount);

        List<Player> playerList = game.getPlayerList();
        List<Player> waitingPlayers = new ArrayList<>();
        List<Player> needBetAgainPlayers = new ArrayList<>();
        int curIndex = -1;
        for (int i = 0; i < playerList.size(); i++) {
            if (id.equals(playerList.get(i).getId())) {
                curIndex = i;
                continue;
            }
            if (curIndex == -1) {
                needBetAgainPlayers.add(playerList.get(i));
            } else {
                waitingPlayers.add(playerList.get(i));
            }
        }
        waitingPlayers.addAll(needBetAgainPlayers);
        game.setCurrentPlayer(waitingPlayers.get(0));
        waitingPlayers.remove(0);
        game.setWaitingPlayers(waitingPlayers);
    }

    // 跟注
    public void call(Game game) {
        bet(game, game.getBiggestBet());

        // 如果都表态完成，即没有等待操作的玩家，则游戏进入下一轮；否则轮到下一个玩家操作
        if (game.getWaitingPlayers().size() == 0) {
            game.goToNextRound();
        } else {
            game.goToNextPlayer();
        }
    }

    // All in
    public void allIn(Game game) {
        bet(game, currentCounter);
        game.getAllInPlayers().add(game.getCurrentPlayer());

        // 如果都表态完成，即没有等待操作的玩家，则游戏进入下一轮
        if (game.getWaitingPlayers().size() == 0) {
            game.goToNextRound();
        }
    }

    // 弃牌
    public void fold(Game game) {
        game.getGamingPlayers().remove(game.getCurrentPlayer());

        // 如果只剩一位玩家，则直接进入游戏结算
        if (game.getGamingPlayers().size() == 1) {
            game.goToClearRound();
        }

        // 如果都表态完成，即没有等待操作的玩家，则游戏进入下一轮
        else if (game.getWaitingPlayers().size() == 0) {
            game.goToNextRound();
        }
    }

    // 过牌
    public void check(Game game) {
        // 如果都表态完成，即没有等待操作的玩家，则游戏进入下一轮
        if (game.getWaitingPlayers().size() == 0) {
            game.goToNextRound();
        }
    }
}
