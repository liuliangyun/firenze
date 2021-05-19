package com.thoughtwork.poker.type;

import com.thoughtwork.poker.Card;
import com.thoughtwork.poker.Point;

import java.util.List;

public class RoyalStraightFlush extends AbstractPokerType {

    public RoyalStraightFlush() {
        this.score = 10;
    }

    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        return 0;
    }

    @Override
    public boolean isMatch(List<Card> cards) {
        List<Integer> cardPoints = getSortedCardPoints(cards);
        return new StraightFlush().isMatch(cards) && cardPoints.get(cardPoints.size() - 1) == Point.ACE.getValue();
    }
}
