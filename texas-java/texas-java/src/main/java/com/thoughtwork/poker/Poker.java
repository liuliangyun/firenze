package com.thoughtwork.poker;

import com.thoughtwork.Player;
import com.thoughtwork.Round;

import java.util.*;
import java.util.stream.Collectors;

public class Poker {
    private List<Card> cardList;
    private List<Card> publicCards; // poker的前5张

    public Poker() {
        this.cardList = createPoker();
        exchangeShuffle();
        this.publicCards = new ArrayList<>();
    }

    // 生成一副不带大小王的牌
    private List<Card> createPoker() {
        List<Card> cards = new ArrayList<>();
        for (Point point : Point.values()) {
            for (Suit suit : Suit.values()) {
                Card card = new Card(suit, point);
                cards.add(card);
            }
        }
        return cards;
    }

    // 交换法洗牌
    private void exchangeShuffle() {
        for (int i = cardList.size() - 1; i > 0; i--) {
            exchange(i, new Random().nextInt(i));
        }
    }

    // 交换两张牌
    private void exchange(int indexOfCard1, int indexOfCard2) {
        Card temp;
        temp = cardList.get(indexOfCard1);
        cardList.set(indexOfCard1, cardList.get(indexOfCard2));
        cardList.set(indexOfCard2, temp);
    }

    // 给玩家依次发牌
    public void dealCardsForPlayers(Player[] players) {
        int i = 5;
        for(int j = 0; j < 2; j++) {
            for(int k = 0; k < players.length; k++) {
                Player player = players[k];
                List<Card> playerCards = player.getCards();
                playerCards.add(cardList.get(i));
                i++;
            }
        }
    }

    // 按轮次翻开公共牌
    public void dealPublicCards(Round currentRound) {
        switch (currentRound) {
            case FLOP:
                publicCards = cardList.subList(0, 3);
                break;
            case TURN:
                publicCards.add(cardList.get(3));
                break;
            case RIVER:
                publicCards.add(cardList.get(4));
                break;
            default:
                break;
        }
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public List<Card> getPublicCards() {
        return publicCards;
    }

    public static int[] pair = {0, 0, 0};
    public static int[] pairOne = {0, 0, 0, 0};

    public int compareCards (List<Card> cards1, List<Card> cards2) {
        int score1 = check(cards1);
        int score2 = check(cards2);
        if (score1 > score2) {
            return 1;
        } else if (score1 < score2) {
            return -1;
        } else {
            List<Integer> cardPoints1 = getCardPoints(cards1);
            List<Integer> cardPoints2 = getCardPoints(cards2);
            Collections.sort(cardPoints1);
            Collections.sort(cardPoints2);

            // 判断同花顺/顺子 --判断最大牌大小
            if (score1 == 9 || score1 == 5) {
                int maxPoint1 = cardPoints1.get(cardPoints1.size() - 1);
                int maxPoint2 = cardPoints2.get(cardPoints2.size() - 1);
                if (maxPoint1 > maxPoint2) {
                    return 1;
                } else if (maxPoint1 < maxPoint2) {
                    return -1;
                } else {
                    return 0;
                }
            }

            // 判断四条/葫芦/三条 --判断总点数
            if (score1 == 8 || score1 == 7 || score1 == 4) {
                int sumPoints1 = getSumPoints(cardPoints1);
                int sumPoints2 = getSumPoints(cardPoints2);
                if (sumPoints1 > sumPoints2) {
                    return 1;
                } else if (sumPoints1 < sumPoints2) {
                    return -1;
                } else {
                    return 0;
                }
            }

            // 判断两对
            // 比较大对子的大小，若相同，比较小对子的大小，若还相同，比较单张牌的大小，若还相同，则为平局。
            if (score1 == 3) {
                twoPairs(cards1);
                int[] pair1 = new int[3];
                pair1[0] = pair[0];
                pair1[1] = pair[1];
                pair1[2] = pair[2];


                twoPairs(cards2);
                int[] pair2 = new int[3];
                pair2[0] = pair[0];
                pair2[1] = pair[1];
                pair2[2] = pair[2];


                if (pair1[1] > pair2[1]) {
                    return 1;
                } else if (pair1[1] < pair2[1]) {
                    return -1;
                } else {
                    if (pair1[0] > pair2[0]) {
                        return 1;
                    } else if (pair1[0] < pair2[0]) {
                        return -1;
                    } else {
                        if (pair1[2] > pair2[2]) {
                            return 1;
                        } else if (pair1[2] < pair2[2]) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            }

            // 判断对子
            if (score1 == 2) {
                pair(cards1);
                int doubleValue1 = pairOne[0];

                pair((cards2));
                int doubleValue2 = pairOne[0];

                if (doubleValue1 > doubleValue2) {
                    return 1;
                } else if (doubleValue1 < doubleValue2) {
                    return -1;
                } else {
                    int count1 = 1;
                    int count2 = 1;
                    for (int i = 0; i < cardPoints1.size(); i++) {
                        if (count1 <= 2 && cardPoints1.get(i).equals(doubleValue1)) {
                            cardPoints1.remove(i);
                            count1++;
                        }
                        if (count2 <= 2 && cardPoints2.get(i).equals(doubleValue2)) {
                            cardPoints2.remove(i);
                            count2++;
                        }
                    }
                    Collections.sort(cardPoints1);
                    Collections.sort(cardPoints2);

                    for (int i = cardPoints1.size() - 1; i > 0; i--) {
                        if (cardPoints1.get(i) > cardPoints2.get(i)) {
                            return 1;
                        } else if (cardPoints1.get(i) < cardPoints2.get(i)) {
                            return -1;
                        }
                    }
                    return 0;
                }
            }

            // 判断散牌
            // 判断同花
            if (score1 == 1 || score1 == 6) {
                for (int i = cardPoints1.size() - 1; i > 0; i--) {
                    if (cardPoints1.get(i) > cardPoints2.get(i)) {
                        return 1;
                    } else if (cardPoints1.get(i) < cardPoints2.get(i)) {
                        return -1;
                    }
                }
                return 0;
            }
        }
        return 0;
    }

    /**
     * 牌总点数
     */
    private int getSumPoints(List<Integer> points) {
        int sum = 0;
        for (int i = 0; i < points.size(); i++) {
            sum += points.get(i);
        }
        return sum;
    }

    private int check(List<Card> cards) {
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
    private int straightFlush(List<Card> cards) {
        if (straight(cards) == 5 && flush(cards) == 6) {
            return 9;
        }
        return 0;
    }

    /**
     * 四条
     */
    private int fourOfAKind(List<Card> cards) {
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
    private int fullHouse(List<Card> cards) {
        if (threeOfAKind(cards) == 4 && pair(cards) == 2) {
            return 7;
        }
        return 0;
    }

    /**
     * 同花
     */
    private int flush(List<Card> cards) {
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
    private int straight(List<Card> cards) {
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
    private int threeOfAKind(List<Card> cards) {
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
    private int twoPairs(List<Card> cards) {
        List<Integer> cardPoints = getCardPoints(cards);
        Collections.sort(cardPoints);
        int count = 0;
        int temp = 1;
        for (int i = 1; i < cardPoints.size(); i++) {
            if (cardPoints.get(i).equals(cardPoints.get(i - 1))) {
                temp++;
            }else {
                pair[2] = cardPoints.get(i - 1);
                temp = 1;
            }
            if (temp == 2) {
                count++;
                pair[count-1] = cardPoints.get(i);
                temp = 1;
            }
        }
        if (count == 2) {
            return 3;
        }
        return 0;
    }


    /**
     * 对子
     */
    private int pair(List<Card> cards) {
        List<Integer> cardPoints = getCardPoints(cards);
        Collections.sort(cardPoints);
        for (int i = 1; i < cardPoints.size(); i++) {
            if (cardPoints.get(i).equals(cardPoints.get(i - 1))) {
                pairOne[0] = cardPoints.get(i);
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
