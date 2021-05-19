package com.thoughtwork.poker.type;

import com.thoughtwork.poker.Card;

import java.util.List;

public class FullHouse extends AbstractPokerType {

    public FullHouse() {
        this.score = 7;
    }

    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        List<Integer> reorderCardPoints1 = getReorderCardPoints(cards1, 3);
        List<Integer> reorderCardPoints2 = getReorderCardPoints(cards2, 3);
        return compareCardPointBySeq(reorderCardPoints1, reorderCardPoints2);
    }

    @Override
    public boolean isMatch(List<Card> cards) {
        return new ThreeOfAKind().isMatch(cards) && new TwoPairs().isMatch(cards);
    }
}
