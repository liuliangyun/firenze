package com.thoughtwork.poker.type;

import com.thoughtwork.poker.Card;

import java.util.List;

public class Straight extends AbstractPokerType {

    public Straight() {
        this.score = 5;
    }

    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getSortedCardPoints(cards1);
        List<Integer> cardPoints2 = getSortedCardPoints(cards2);
        return compareCardPointByMax(cardPoints1, cardPoints2);
    }

    @Override
    public boolean isMatch(List<Card> cards) {
        List<Integer> cardPoints = getSortedCardPoints(cards);

        for (int i = 1; i < cardPoints.size(); i++) {
            if (cardPoints.get(i) - cardPoints.get(i - 1) != 1) {
                return false;
            }
        }
        return true;
    }
}
