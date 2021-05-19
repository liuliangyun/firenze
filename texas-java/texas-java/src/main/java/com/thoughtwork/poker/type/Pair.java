package com.thoughtwork.poker.type;

import com.thoughtwork.poker.Card;

import java.util.List;
import java.util.Map;

public class Pair extends AbstractPokerType {

    public Pair() {
        this.score = 2;
    }

    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        List<Integer> reorderCardPoints1 = getReorderCardPoints(cards1, 2);
        List<Integer> reorderCardPoints2 = getReorderCardPoints(cards2, 2);
        return compareCardPointBySeq(reorderCardPoints1, reorderCardPoints2);
    }

    @Override
    public boolean isMatch(List<Card> cards) {
        Map<Integer, Integer> cardPointCount = getSortedCardPointCount(cards);
        return cardPointCount.values().stream().filter(count -> count >= 2).count() >= 1;
    }
}
