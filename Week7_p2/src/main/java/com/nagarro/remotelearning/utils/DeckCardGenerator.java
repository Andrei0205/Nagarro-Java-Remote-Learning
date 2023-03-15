package com.nagarro.remotelearning.utils;

import java.util.ArrayList;
import java.util.List;


public class DeckCardGenerator {
    private static final int LAST_CARD = 14;

    public List<Card> getDeck() {
        List<Card> deck = new ArrayList<>();
        for (int index = 1; index < LAST_CARD; index++) {
            for (Suite suite : Suite.values()){
                deck.add(new Card(index, suite));
            }
        }
        return deck;
    }

}
