package com.thoughtwork.poker;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PokerUtils {
    // 7张牌取5张牌，取最佳得分作为最终得分
    // cards 长度为7
    public static int getBestScore(List<Card> cards) {
        int bestScore = 0;
        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                List<Card> cards5 = new ArrayList<>();
                cards5.addAll(cards.subList(0, i));
                cards5.addAll(cards.subList(i + 1, j));
                cards5.addAll(cards.subList(j + 1, cards.size()));
                int score = getScore(cards5);
                if (score > bestScore) {
                    bestScore = score;
                }
            }
        }
        return bestScore;
    }

    // 获得5张牌的得分，不同的牌型对应不同的得分
    // cards 长度为5
    private static int getScore(List<Card> cards) {
        List<Integer> resultList = new ArrayList<>();
        resultList.add(royalStraightFlush(cards));
        resultList.add(straightFlush(cards));
        resultList.add(fourOfAKind(cards));
        resultList.add(fullHouse(cards));
        resultList.add(flush(cards));
        resultList.add(straight(cards));
        resultList.add(threeOfAKind(cards));
        resultList.add(twoPairs(cards));
        resultList.add(pair(cards));
        resultList.add(1);
        Collections.sort(resultList);
        return resultList.get(resultList.size() - 1);
    }

    /**
     * 皇家同花顺
     */
    private static int royalStraightFlush(List<Card> cards) {
        if (straightFlush(cards) == PokerResult.Straight_Flush.getValue()) {
            List<Integer> cardPoints = getCardPoints(cards);
            Collections.sort(cardPoints);
            if (cardPoints.get(cardPoints.size() - 1) == Point.ACE.getValue()) {
                return PokerResult.Royal_Straight_Flush.getValue();
            }
        }
        return 0;
    }

    /**
     * 同花顺
     */
    private static int straightFlush(List<Card> cards) {
        if (straight(cards) == PokerResult.Straight.getValue()
                && flush(cards) == PokerResult.Flush.getValue()) {
            return PokerResult.Straight_Flush.getValue();
        }
        return 0;
    }

    /**
     * 四条
     */
    private static int fourOfAKind(List<Card> cards) {
        List<Integer> cardPoints = getCardPoints(cards);
        Collections.sort(cardPoints);
        int count = 1;
        for (int i = 1; i < cardPoints.size(); i++) {
            if (cardPoints.get(i).equals(cardPoints.get(i - 1))) {
                count++;
            } else {
                count = 1;
            }
            if (count >= 4) {
                return PokerResult.Four_Of_A_Kind.getValue();
            }
        }
        return 0;
    }

    /**
     * 葫芦
     */
    private static int fullHouse(List<Card> cards) {
        if (threeOfAKind(cards) == PokerResult.Three_Of_A_Kind.getValue()
                && twoPairs(cards) == PokerResult.Two_Pair.getValue()) {
            return PokerResult.Full_House.getValue();
        }
        return 0;
    }

    /**
     * 同花
     */
    private static int flush(List<Card> cards) {
        List<String> cardColors = getCardColors(cards);
        for (int i = 1; i < cardColors.size(); i++) {
            if (!cardColors.get(i).equals(cardColors.get(i - 1))) {
                return 0;
            }
        }
        return PokerResult.Flush.getValue();
    }

    /**
     * 顺子
     */
    private static int straight(List<Card> cards) {
        List<Integer> cardPoints = getCardPoints(cards);
        Collections.sort(cardPoints);

        for (int i = 1; i < cardPoints.size(); i++) {
            if (cardPoints.get(i) - cardPoints.get(i - 1) != 1) {
                return 0;
            }
        }
        return PokerResult.Straight.getValue();
    }

    /**
     * 三条
     */
    private static int threeOfAKind(List<Card> cards) {
        List<Integer> cardPoints = getCardPoints(cards);
        Collections.sort(cardPoints);
        int count = 1;
        for (int i = 1; i < cardPoints.size(); i++) {
            if (cardPoints.get(i).equals(cardPoints.get(i - 1))) {
                count++;
            }else {
                count = 1;
            }
            if (count == 3) {
                return PokerResult.Three_Of_A_Kind.getValue();
            }
        }
        return 0;
    }

    /**
     * 两对
     */
    private static int twoPairs(List<Card> cards) {
        List<Integer> cardPoints = getCardPoints(cards);
        Map<Integer, Integer> cardPointCount = new HashMap<>();
        AtomicInteger pairCount = new AtomicInteger();
        cardPoints.forEach(cardPoint -> {
            if (cardPointCount.containsKey(cardPoint)) {
                int count = cardPointCount.get(cardPoint) + 1;
                if (count == 2) {
                    pairCount.getAndIncrement();
                }
                cardPointCount.put(cardPoint, count);
            } else {
                cardPointCount.put(cardPoint, 1);
            }
        });

        if (pairCount.get() == 2) {
            return PokerResult.Two_Pair.getValue();
        }
        return 0;
    }


    /**
     * 对子
     */
    private static int pair(List<Card> cards) {
        List<Integer> cardPoints = getCardPoints(cards);
        Collections.sort(cardPoints);
        for (int i = 1; i < cardPoints.size(); i++) {
            if (cardPoints.get(i).equals(cardPoints.get(i - 1))) {
                return PokerResult.Pair.getValue();
            }
        }
        return 0;
    }

    private static List<Integer> getCardPoints(List<Card> cards) {
        return cards.stream().map(card -> card.getPoint().getValue()).collect(Collectors.toList());
    }

    private static List<String> getCardColors(List<Card> cards) {
        return cards.stream().map(card -> card.getSuit().getValue()).collect(Collectors.toList());
    }

}
