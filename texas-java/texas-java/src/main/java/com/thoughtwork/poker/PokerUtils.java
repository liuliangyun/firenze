package com.thoughtwork.poker;

import com.thoughtwork.poker.type.*;

import java.util.*;

import static java.util.Objects.isNull;

public class PokerUtils {
    // 7张牌取5张最佳组合牌
    // cards 长度为7
    public static BestResult getBestResult(List<Card> cards) {
        BestResult bestResult = null;
        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                List<Card> cards5 = new ArrayList<>();
                cards5.addAll(cards.subList(0, i));
                cards5.addAll(cards.subList(i + 1, j));
                cards5.addAll(cards.subList(j + 1, cards.size()));

                AbstractPokerType type = getPokerType(cards5);
                if (isNull(bestResult)) {
                    bestResult = new BestResult();
                    bestResult.setPokerType(type);
                    bestResult.setBestCards(cards5);
                } else if (type.getScore() > bestResult.getPokerType().getScore()) {
                    bestResult.setPokerType(type);
                    bestResult.setBestCards(cards5);
                } else if (type.getScore() == bestResult.getPokerType().getScore()) {
                    if (type.compare(cards5, bestResult.getBestCards()) > 0) {
                        bestResult.setBestCards(cards5);
                    }
                }
            }
        }
        return bestResult;
    }

    // 根据5张牌获得牌型
    // cards 长度为5
    private static AbstractPokerType getPokerType(List<Card> cards) {
        if (new RoyalStraightFlush().isMatch(cards)) {
            return new RoyalStraightFlush();
        } else if (new StraightFlush().isMatch(cards)) {
            return new StraightFlush();
        } else if (new FourOfAKind().isMatch(cards)) {
            return new FourOfAKind();
        } else if (new FullHouse().isMatch(cards)) {
            return new FullHouse();
        } else if (new Flush().isMatch(cards)) {
            return new Flush();
        } else if (new Straight().isMatch(cards)) {
            return new Straight();
        } else if (new ThreeOfAKind().isMatch(cards)) {
            return new ThreeOfAKind();
        } else if (new TwoPairs().isMatch(cards)) {
            return new TwoPairs();
        } else if (new Pair().isMatch(cards)) {
            return new Pair();
        } else {
            return new HighCard();
        }
    }

}
