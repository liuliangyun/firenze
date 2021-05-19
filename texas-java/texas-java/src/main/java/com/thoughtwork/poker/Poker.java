package com.thoughtwork.poker;

import com.thoughtwork.Player;
import com.thoughtwork.Round;

import java.util.*;

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

    // 剩余玩家翻牌，分别计算5张牌的最佳组合得分
    public void showdown(List<Player> activePlayers) {
        activePlayers.forEach(player -> {
            List<Card> cards = new ArrayList<>();
            cards.addAll(player.getCards());
            cards.addAll(publicCards);

            BestResult result = PokerUtils.getBestResult(cards);
            player.setBestResult(result);
        });
    }
}
