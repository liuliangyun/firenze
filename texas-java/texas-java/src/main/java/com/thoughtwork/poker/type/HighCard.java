package com.thoughtwork.poker.type;

import com.thoughtwork.poker.Card;

import java.util.List;

public class HighCard extends AbstractPokerType {

    public HighCard() {
        this.score = 1;
    }

    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getSortedCardPoints(cards1);
        List<Integer> cardPoints2 = getSortedCardPoints(cards2);
        return compareCardPointBySeq(cardPoints1, cardPoints2);
    }

    @Override
    public boolean isMatch(List<Card> cards) {
        return true;
    }
}
