package com.thoughtwork.poker;

import java.util.Objects;

public class Card implements Comparable {
    private Suit suit;
    private Point point;

    public Card(Suit suit, Point point) {
        this.suit = suit;
        this.point = point;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public int compareTo(Object otherCard) {
        Card other = (Card) otherCard;
        return this.point.ordinal() - other.getPoint().ordinal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit == card.suit &&
                point == card.point;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, point);
    }
}
