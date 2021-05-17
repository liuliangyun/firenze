package com.thoughtwork.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
     * 同花顺
     */
    private static int straightFlush(List<Card> cards) {
        if (straight(cards) == 5 && flush(cards) == 6) {
            return 9;
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
                return 8;
            }
        }
        return 0;
    }

    /**
     * 葫芦
     */
    private static int fullHouse(List<Card> cards) {
        if (threeOfAKind(cards) == 4 && pair(cards) == 2) {
            return 7;
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
        return 6;
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
        return 5;
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
                return 4;
            }
        }
        return 0;
    }

    /**
     * 两对
     */
    private static int twoPairs(List<Card> cards) {
        List<Integer> cardPoints = getCardPoints(cards);
        Collections.sort(cardPoints);
        int count = 0;
        int temp = 1;
        for (int i = 1; i < cardPoints.size(); i++) {
            if (cardPoints.get(i).equals(cardPoints.get(i - 1))) {
                temp++;
            } else {
                temp = 1;
            }
            if (temp == 2) {
                count++;
                temp = 1;
            }
            if (count == 2) {
                return 3;
            }
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
                return 2;
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
