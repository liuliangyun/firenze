package com.thoughtwork;

import com.thoughtwork.poker.Card;
import com.thoughtwork.poker.PokerResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private String name;
    private boolean active;
    private boolean tookAction;

    private boolean isWin;
    private int award;

    //手牌
    private List<Card> cards = new ArrayList<>();
    //玩家最佳组合牌型
    private PokerResult pokerResult = null;

    public Player(String name) {
        this.name = name;
        this.active = true;
        this.tookAction = false;
        this.isWin = false;
        this.award = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isTookAction() {
        return tookAction;
    }

    public void setTookAction(boolean tookAction) {
        this.tookAction = tookAction;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public PokerResult getPokerResult() {
        return pokerResult;
    }

    public void setPokerResult(PokerResult pokerResult) {
        this.pokerResult = pokerResult;
    }
}
