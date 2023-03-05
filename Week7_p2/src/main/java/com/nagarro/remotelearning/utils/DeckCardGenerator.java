package com.nagarro.remotelearning.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DeckCardGenerator {
    private final List<String> suits = Arrays.asList("Clover", "Diamond", "Red", "Black");

    public List<Card> getDeck() {
        List<Card> deck = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            for (int j = 0; j < 4; j++){
                deck.add(new Card(i, suits.get(j)));
            }
        }
        return deck;
    }

}
