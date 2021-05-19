package com.thoughtwork.poker.type;

import com.thoughtwork.poker.Card;

import java.util.List;
import java.util.Map;

public class FourOfAKind extends AbstractPokerType {

    public FourOfAKind() {
        this.score = 8;
    }

    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        List<Integer> reorderCardPoints1 = getReorderCardPoints(cards1, 4);
        List<Integer> reorderCardPoints2 = getReorderCardPoints(cards2, 4);
        return compareCardPointBySeq(reorderCardPoints1, reorderCardPoints2);
    }

    @Override
    public boolean isMatch(List<Card> cards) {
        Map<Integer, Integer> cardPointCount = getSortedCardPointCount(cards);
        return cardPointCount.values().stream().filter(count -> count >= 4).count() == 1;
    }
}
