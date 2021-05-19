package com.thoughtwork.poker;

import com.thoughtwork.poker.type.AbstractPokerType;
import java.util.List;

public class BestResult {

    private List<Card> bestCards;
    private AbstractPokerType pokerType;

    public List<Card> getBestCards() {
        return bestCards;
    }

    public void setBestCards(List<Card> bestCards) {
        this.bestCards = bestCards;
    }

    public AbstractPokerType getPokerType() {
        return pokerType;
    }

    public void setPokerType(AbstractPokerType pokerType) {
        this.pokerType = pokerType;
    }
}
