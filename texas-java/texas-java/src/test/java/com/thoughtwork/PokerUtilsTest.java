package com.thoughtwork;

import com.thoughtwork.poker.*;
import com.thoughtwork.poker.type.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PokerUtilsTest {

    @Test
    public void should_be_royal_straight_flush () {
        List<Card> cards = Arrays.asList(
                new Card(Suit.SPADE, Point.TEN),
                new Card(Suit.HEART, Point.JACK),
                new Card(Suit.HEART, Point.TEN),
                new Card(Suit.SPADE, Point.JACK),
                new Card(Suit.SPADE, Point.QUEEN),
                new Card(Suit.SPADE, Point.KING),
                new Card(Suit.SPADE, Point.ACE)
        );
        assertEquals(new RoyalStraightFlush().getScore(), PokerUtils.getBestResult(cards).getPokerType().getScore());
    }

    @Test
    public void should_be_straight_flush () {
        List<Card> cards = Arrays.asList(
                new Card(Suit.SPADE, Point.TEN),
                new Card(Suit.HEART, Point.JACK),
                new Card(Suit.HEART, Point.TEN),
                new Card(Suit.SPADE, Point.NINE),
                new Card(Suit.SPADE, Point.EIGHT),
                new Card(Suit.SPADE, Point.SEVEN),
                new Card(Suit.SPADE, Point.SIX)
        );
        assertEquals(new StraightFlush().getScore(), PokerUtils.getBestResult(cards).getPokerType().getScore());
    }

    @Test
    public void should_be_four_of_a_kind () {
        List<Card> cards = Arrays.asList(
                new Card(Suit.SPADE, Point.TWO),
                new Card(Suit.HEART, Point.QUEEN),
                new Card(Suit.CLUB, Point.QUEEN),
                new Card(Suit.SPADE, Point.QUEEN),
                new Card(Suit.DIAMOND, Point.QUEEN),
                new Card(Suit.SPADE, Point.THREE),
                new Card(Suit.SPADE, Point.FOUR)
        );
        assertEquals(new FourOfAKind().getScore(), PokerUtils.getBestResult(cards).getPokerType().getScore());
    }

    @Test
    public void should_be_full_house () {
        List<Card> cards = Arrays.asList(
                new Card(Suit.SPADE, Point.JACK),
                new Card(Suit.HEART, Point.KING),
                new Card(Suit.HEART, Point.JACK),
                new Card(Suit.DIAMOND, Point.JACK),
                new Card(Suit.SPADE, Point.KING),
                new Card(Suit.SPADE, Point.THREE),
                new Card(Suit.SPADE, Point.FOUR)
        );
        assertEquals(new FullHouse().getScore(), PokerUtils.getBestResult(cards).getPokerType().getScore());
    }

    @Test
    public void should_be_flush () {
        List<Card> cards = Arrays.asList(
                new Card(Suit.SPADE, Point.JACK),
                new Card(Suit.HEART, Point.FOUR),
                new Card(Suit.HEART, Point.THREE),
                new Card(Suit.SPADE, Point.TEN),
                new Card(Suit.SPADE, Point.KING),
                new Card(Suit.SPADE, Point.THREE),
                new Card(Suit.SPADE, Point.FOUR)
        );
        assertEquals(new Flush().getScore(), PokerUtils.getBestResult(cards).getPokerType().getScore());
    }

    @Test
    public void should_be_straight () {
        List<Card> cards = Arrays.asList(
                new Card(Suit.SPADE, Point.FIVE),
                new Card(Suit.HEART, Point.SEVEN),
                new Card(Suit.HEART, Point.EIGHT),
                new Card(Suit.SPADE, Point.SIX),
                new Card(Suit.CLUB, Point.NINE),
                new Card(Suit.SPADE, Point.TWO),
                new Card(Suit.SPADE, Point.FOUR)
        );
        assertEquals(new Straight().getScore(), PokerUtils.getBestResult(cards).getPokerType().getScore());
    }

    @Test
    public void should_be_three_of_a_kind () {
        List<Card> cards = Arrays.asList(
                new Card(Suit.SPADE, Point.SIX),
                new Card(Suit.HEART, Point.TEN),
                new Card(Suit.HEART, Point.EIGHT),
                new Card(Suit.SPADE, Point.TEN),
                new Card(Suit.CLUB, Point.TEN),
                new Card(Suit.SPADE, Point.TWO),
                new Card(Suit.SPADE, Point.FOUR)
        );
        assertEquals(new ThreeOfAKind().getScore(), PokerUtils.getBestResult(cards).getPokerType().getScore());
    }

    @Test
    public void should_be_two_pair () {
        List<Card> cards = Arrays.asList(
                new Card(Suit.SPADE, Point.SIX),
                new Card(Suit.HEART, Point.TEN),
                new Card(Suit.HEART, Point.SIX),
                new Card(Suit.SPADE, Point.TEN),
                new Card(Suit.CLUB, Point.NINE),
                new Card(Suit.SPADE, Point.TWO),
                new Card(Suit.SPADE, Point.FOUR)
        );
        assertEquals(new TwoPairs().getScore(), PokerUtils.getBestResult(cards).getPokerType().getScore());
    }

    @Test
    public void should_be_pair () {
        List<Card> cards = Arrays.asList(
                new Card(Suit.SPADE, Point.SIX),
                new Card(Suit.HEART, Point.THREE),
                new Card(Suit.HEART, Point.SIX),
                new Card(Suit.SPADE, Point.JACK),
                new Card(Suit.CLUB, Point.NINE),
                new Card(Suit.SPADE, Point.TWO),
                new Card(Suit.SPADE, Point.FOUR)
        );
        assertEquals(new Pair().getScore(), PokerUtils.getBestResult(cards).getPokerType().getScore());
    }

    @Test
    public void should_be_high_card () {
        List<Card> cards = Arrays.asList(
                new Card(Suit.SPADE, Point.SIX),
                new Card(Suit.HEART, Point.THREE),
                new Card(Suit.HEART, Point.QUEEN),
                new Card(Suit.SPADE, Point.JACK),
                new Card(Suit.CLUB, Point.NINE),
                new Card(Suit.SPADE, Point.TWO),
                new Card(Suit.SPADE, Point.FOUR)
        );
        assertEquals(new HighCard().getScore(), PokerUtils.getBestResult(cards).getPokerType().getScore());
    }

}
