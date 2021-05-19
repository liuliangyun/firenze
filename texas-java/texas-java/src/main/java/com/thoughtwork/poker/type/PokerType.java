package com.thoughtwork.poker.type;

import com.thoughtwork.poker.Card;

import java.util.List;

public interface PokerType {
    int compare (List<Card> cards1, List<Card> cards2);

    boolean isMatch (List<Card> cards);
}
