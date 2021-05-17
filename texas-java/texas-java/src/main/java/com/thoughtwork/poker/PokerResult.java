package com.thoughtwork.poker;

public enum PokerResult {
    Royal_Straight_Flush(10),
    Straight_Flush(9),
    Four_Of_A_Kind(8),
    Full_House(7),
    Flush(6),
    Straight(5),
    Three_Of_A_Kind(4),
    Two_Pair(3),
    Pair(2),
    High_Card(1);

    private int value;

    PokerResult(int value) {
        this.value = value;
    }

    public static PokerResult valueOf (int value) {
        for (PokerResult result : PokerResult.values()) {
            if (result.value == value) {
                return result;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
