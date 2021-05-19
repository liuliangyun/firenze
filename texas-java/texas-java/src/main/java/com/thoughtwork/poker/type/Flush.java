package com.thoughtwork.poker.type;

import com.thoughtwork.poker.Card;

import java.util.List;

public class Flush extends AbstractPokerType {

    public Flush() {
        this.score = 6;
    }

    @Override
    public int compare(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getSortedCardPoints(cards1);
        List<Integer> cardPoints2 = getSortedCardPoints(cards2);
        return compareCardPointBySeq(cardPoints1, cardPoints2);
    }

    @Override
    public boolean isMatch(List<Card> cards) {
        List<String> cardColors = getCardColors(cards);
        return cardColors.stream().distinct().count() == 1;
    }
}
