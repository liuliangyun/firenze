package com.thoughtwork.poker.type;

import com.thoughtwork.poker.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractPokerType implements PokerType {
    protected int score;

    protected List<Integer> getSortedCardPoints(List<Card> cards) {
        return cards.stream().map(card -> card.getPoint().getValue()).sorted().collect(Collectors.toList());
    }

    protected List<String> getCardColors(List<Card> cards) {
        return cards.stream().map(card -> card.getSuit().getValue()).collect(Collectors.toList());
    }

    protected Map<Integer, Integer> getSortedCardPointCount(List<Card> cards) {
        List<Integer> cardPoints = getSortedCardPoints(cards);
        Map<Integer, Integer> cardPointCount = new HashMap<>();
        cardPoints.forEach(cardPoint -> {
            if (cardPointCount.containsKey(cardPoint)) {
                int count = cardPointCount.get(cardPoint) + 1;
                cardPointCount.put(cardPoint, count);
            } else {
                cardPointCount.put(cardPoint, 1);
            }
        });
        return cardPointCount;
    }

    protected List<Integer> getReorderCardPoints(List<Card> cards, int pairValue) {
        Map<Integer, Integer> cardPointCount = getSortedCardPointCount(cards);
        List<Integer> pointsForPair = new ArrayList<>();
        List<Integer> pointsForAnother = new ArrayList<>();
        for (int key: cardPointCount.keySet()) {
            if (cardPointCount.get(key) == pairValue) {
                pointsForPair.add(key);
            } else {
                pointsForAnother.add(key);
            }
        }
        pointsForPair.addAll(pointsForAnother);
        return pointsForPair;
    }

    protected int compareCardPointByMax(List<Integer> cardPoints1, List<Integer> cardPoints2) {
        if (cardPoints1.get(cardPoints1.size() - 1) > cardPoints2.get(cardPoints2.size() - 1)) {
            return 1;
        } else if (cardPoints1.get(cardPoints1.size() - 1) < cardPoints2.get(cardPoints2.size() - 1)) {
            return -1;
        }
        return 0;
    }

    protected int compareCardPointBySeq(List<Integer> cardPoints1, List<Integer> cardPoints2) {
        for (int i = 0; i < cardPoints1.size(); i++) {
            if (cardPoints1.get(i) > cardPoints2.get(i)) {
                return 1;
            } else if (cardPoints1.get(i) < cardPoints2.get(i)) {
                return -1;
            }
        }
        return 0;
    }

    public int getScore() {
        return this.score;
    }

}
