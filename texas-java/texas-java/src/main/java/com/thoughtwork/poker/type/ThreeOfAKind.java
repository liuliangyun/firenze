package com.thoughtwork.poker.type;

import com.thoughtwork.poker.Card;

import java.util.List;
import java.util.Map;

public class ThreeOfAKind extends AbstractPokerType {

    public ThreeOfAKind() {
        this.score = 4;
    }

    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        List<Integer> reorderCardPoints1 = getReorderCardPoints(cards1, 3);
        List<Integer> reorderCardPoints2 = getReorderCardPoints(cards2, 3);
        return compareCardPointBySeq(reorderCardPoints1, reorderCardPoints2);
    }

    @Override
    public boolean isMatch(List<Card> cards) {
        Map<Integer, Integer> cardPointCount = getSortedCardPointCount(cards);
        return cardPointCount.values().stream().filter(count -> count >= 3).count() == 1;
    }
}
