package com.thoughtwork.poker;

public enum Suit {
    DIAMOND("♦"),
    CLUB("♣"),
    HEART("♥"),
    SPADE("♠");

    private String value;

    Suit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
