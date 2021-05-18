package com.thoughtwork.poker;

import java.util.*;
import java.util.stream.Collectors;

public class PokerUtils {
    // 7张牌取5张牌，取最佳得分作为最终得分
    // cards 长度为7
    public static int getBestScore(List<Card> cards) {
        int bestScore = 0;
        List<Card> bestCards = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                List<Card> cards5 = new ArrayList<>();
                cards5.addAll(cards.subList(0, i));
                cards5.addAll(cards.subList(i + 1, j));
                cards5.addAll(cards.subList(j + 1, cards.size()));
                int score = getScore(cards5);
                if (score > bestScore) {
                    bestScore = score;
                    bestCards.clear();
                    bestCards.addAll(cards5);
                } else if (score == bestScore && compareCardsPoint(score, bestCards, cards5) == -1) {
                    bestCards.clear();
                    bestCards.addAll(cards5);
                }
            }
        }
        return bestScore;
    }

    private static int compareCardsPoint(int score, List<Card> cards1, List<Card> cards2) {
        if (score == 10) {
            return compareCardsPointForRoyalStraightFlush(cards1, cards2);
        } else if (score == 9) {
            return compareCardsPointForStraightFlush(cards1, cards2);
        } else if (score == 8) {
            return compareCardsPointForFourOfAKind(cards1, cards2);
        } else if (score == 7) {
            return compareCardsPointForFullHouse(cards1, cards2);
        } else if (score == 6) {
            return compareCardsPointForFlush(cards1, cards2);
        } else if (score == 5) {
            return compareCardsPointForStraight(cards1, cards2);
        } else if (score == 4) {
            return compareCardsPointForThreeOfAKind(cards1, cards2);
        } else if (score == 3) {
            return compareCardsPointForTwoPairs(cards1, cards2);
        } else if (score == 2) {
            return compareCardsPointForPair(cards1, cards2);
        } else {
            return compareCardsPointForHighCard(cards1, cards2);
        }
    }

    private static int compareCardsPointForRoyalStraightFlush(List<Card> cards1, List<Card> cards2) {
        return 0;
    }

    private static int compareCardsPointForStraightFlush(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getCardPoints(cards1);
        List<Integer> cardPoints2 = getCardPoints(cards2);
        Collections.sort(cardPoints1);
        Collections.sort(cardPoints2);

        return compareCardPointByMax(cardPoints1, cardPoints2);
    }

    private static int compareCardsPointForFourOfAKind(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getCardPoints(cards1);
        List<Integer> cardPoints2 = getCardPoints(cards2);
        Collections.sort(cardPoints1);
        Collections.sort(cardPoints2);
        Map<Integer, Integer> cardPointCount1 = getCardPointCount(cards1);
        Map<Integer, Integer> cardPointCount2 = getCardPointCount(cards2);

        List<Integer> pointForFour1 = new ArrayList<>();
        List<Integer> anotherPoints1 = new ArrayList<>();
        for (int key: cardPointCount1.keySet()) {
            if (cardPointCount1.get(key) == 4) {
                pointForFour1.add(key);
            } else {
                anotherPoints1.add(key);
            }
        }
        List<Integer> pointForFour2 = new ArrayList<>();
        List<Integer> anotherPoints2 = new ArrayList<>();
        for (int key: cardPointCount2.keySet()) {
            if (cardPointCount2.get(key) == 4) {
                pointForFour2.add(key);
            } else {
                anotherPoints2.add(key);
            }
        }

        Collections.sort(anotherPoints1);
        Collections.sort(anotherPoints2);
        Collections.sort(pointForFour1);
        Collections.sort(pointForFour2);
        pointForFour1.addAll(anotherPoints1);
        pointForFour2.addAll(anotherPoints2);
        return compareCardPointBySeq(pointForFour1, pointForFour2);
    }

    private static int compareCardsPointForFullHouse(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getCardPoints(cards1);
        List<Integer> cardPoints2 = getCardPoints(cards2);
        Collections.sort(cardPoints1);
        Collections.sort(cardPoints2);
        Map<Integer, Integer> cardPointCount1 = getCardPointCount(cards1);
        Map<Integer, Integer> cardPointCount2 = getCardPointCount(cards2);

        List<Integer> pointForThree1 = new ArrayList<>();
        List<Integer> anotherPoints1 = new ArrayList<>();
        for (int key: cardPointCount1.keySet()) {
            if (cardPointCount1.get(key) == 3) {
                pointForThree1.add(key);
            } else {
                anotherPoints1.add(key);
            }
        }

        List<Integer> pointForThree2 = new ArrayList<>();
        List<Integer> anotherPoints2 = new ArrayList<>();
        for (int key: cardPointCount2.keySet()) {
            if (cardPointCount2.get(key) == 3) {
                pointForThree2.add(key);
            } else {
                anotherPoints2.add(key);
            }
        }

        Collections.sort(anotherPoints1);
        Collections.sort(anotherPoints2);
        Collections.sort(pointForThree1);
        Collections.sort(pointForThree2);
        pointForThree1.addAll(anotherPoints1);
        pointForThree2.addAll(anotherPoints2);

        return compareCardPointBySeq(pointForThree1, pointForThree2);
    }

    private static int compareCardsPointForFlush(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getCardPoints(cards1);
        List<Integer> cardPoints2 = getCardPoints(cards2);
        Collections.sort(cardPoints1);
        Collections.sort(cardPoints2);

        return compareCardPointBySeq(cardPoints1, cardPoints2);
    }

    private static int compareCardsPointForStraight(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getCardPoints(cards1);
        List<Integer> cardPoints2 = getCardPoints(cards2);
        Collections.sort(cardPoints1);
        Collections.sort(cardPoints2);

        return compareCardPointByMax(cardPoints1, cardPoints2);
    }

    private static int compareCardsPointForThreeOfAKind(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getCardPoints(cards1);
        List<Integer> cardPoints2 = getCardPoints(cards2);
        Collections.sort(cardPoints1);
        Collections.sort(cardPoints2);
        Map<Integer, Integer> cardPointCount1 = getCardPointCount(cards1);
        Map<Integer, Integer> cardPointCount2 = getCardPointCount(cards2);

        List<Integer> pointForThree1 = new ArrayList<>();
        List<Integer> anotherPoints1 = new ArrayList<>();
        for (int key: cardPointCount1.keySet()) {
            if (cardPointCount1.get(key) == 3) {
                pointForThree1.add(key);
            } else {
                anotherPoints1.add(key);
            }
        }

        List<Integer> pointForThree2 = new ArrayList<>();
        List<Integer> anotherPoints2 = new ArrayList<>();
        for (int key: cardPointCount2.keySet()) {
            if (cardPointCount2.get(key) == 3) {
                pointForThree2.add(key);
            } else {
                anotherPoints2.add(key);
            }
        }

        Collections.sort(anotherPoints1);
        Collections.sort(anotherPoints2);
        Collections.sort(pointForThree1);
        Collections.sort(pointForThree2);
        pointForThree1.addAll(anotherPoints1);
        pointForThree2.addAll(anotherPoints2);

        return compareCardPointBySeq(pointForThree1, pointForThree2);
    }

    private static int compareCardsPointForTwoPairs(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getCardPoints(cards1);
        List<Integer> cardPoints2 = getCardPoints(cards2);
        Collections.sort(cardPoints1);
        Collections.sort(cardPoints2);
        Map<Integer, Integer> cardPointCount1 = getCardPointCount(cards1);
        Map<Integer, Integer> cardPointCount2 = getCardPointCount(cards2);

        List<Integer> pointForTwo1 = new ArrayList<>();
        List<Integer> anotherPoints1 = new ArrayList<>();
        for (int key: cardPointCount1.keySet()) {
            if (cardPointCount1.get(key) == 2) {
                pointForTwo1.add(key);
            } else {
                anotherPoints1.add(key);
            }
        }

        List<Integer> pointForTwo2 = new ArrayList<>();
        List<Integer> anotherPoints2 = new ArrayList<>();
        for (int key: cardPointCount2.keySet()) {
            if (cardPointCount2.get(key) == 2) {
                pointForTwo2.add(key);
            } else {
                anotherPoints2.add(key);
            }
        }

        Collections.sort(anotherPoints1);
        Collections.sort(anotherPoints2);
        Collections.sort(pointForTwo1);
        Collections.sort(pointForTwo2);
        pointForTwo1.addAll(anotherPoints1);
        pointForTwo2.addAll(anotherPoints2);

        return compareCardPointBySeq(pointForTwo1, pointForTwo2);
    }

    private static int compareCardsPointForPair(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getCardPoints(cards1);
        List<Integer> cardPoints2 = getCardPoints(cards2);
        Collections.sort(cardPoints1);
        Collections.sort(cardPoints2);
        Map<Integer, Integer> cardPointCount1 = getCardPointCount(cards1);
        Map<Integer, Integer> cardPointCount2 = getCardPointCount(cards2);

        List<Integer> pointForTwo1 = new ArrayList<>();
        List<Integer> anotherPoints1 = new ArrayList<>();
        for (int key: cardPointCount1.keySet()) {
            if (cardPointCount1.get(key) == 2) {
                pointForTwo1.add(key);
            } else {
                anotherPoints1.add(key);
            }
        }

        List<Integer> pointForTwo2 = new ArrayList<>();
        List<Integer> anotherPoints2 = new ArrayList<>();
        for (int key: cardPointCount2.keySet()) {
            if (cardPointCount2.get(key) == 2) {
                pointForTwo2.add(key);
            } else {
                anotherPoints2.add(key);
            }
        }

        Collections.sort(anotherPoints1);
        Collections.sort(anotherPoints2);
        Collections.sort(pointForTwo1);
        Collections.sort(pointForTwo2);
        pointForTwo1.addAll(anotherPoints1);
        pointForTwo2.addAll(anotherPoints2);

        return compareCardPointBySeq(pointForTwo1, pointForTwo2);
    }

    private static int compareCardsPointForHighCard(List<Card> cards1, List<Card> cards2) {
        List<Integer> cardPoints1 = getCardPoints(cards1);
        List<Integer> cardPoints2 = getCardPoints(cards2);
        Collections.sort(cardPoints1);
        Collections.sort(cardPoints2);

        return compareCardPointBySeq(cardPoints1, cardPoints2);
    }

    private static int compareCardPointBySeq(List<Integer> cardPoints1, List<Integer> cardPoints2) {
        for (int i = 0; i < cardPoints1.size(); i++) {
            if (cardPoints1.get(i) > cardPoints2.get(i)) {
                return 1;
            } else if (cardPoints1.get(i) < cardPoints2.get(i)) {
                return -1;
            }
        }
        return 0;
    }

    private static int compareCardPointByMax(List<Integer> cardPoints1, List<Integer> cardPoints2) {
        if (cardPoints1.get(cardPoints1.size() - 1) > cardPoints2.get(cardPoints2.size() - 1)) {
            return 1;
        } else if (cardPoints1.get(cardPoints1.size() - 1) < cardPoints2.get(cardPoints2.size() - 1)) {
            return -1;
        }
        return 0;
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
        Map<Integer, Integer> cardPointCount = getCardPointCount(cards);
        if (cardPointCount.values().stream().filter(count -> count >= 2).count() == 2) {
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

    private static Map<Integer, Integer> getCardPointCount(List<Card> cards) {
        List<Integer> cardPoints = getCardPoints(cards);
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

}
