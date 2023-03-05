package com.nagarro.remotelearning.utils;

public class Card {
    private int number;
    private String suite;

    public int getNumber() {
        return number;
    }

    public String getSuite() {
        return suite;
    }

    public Card(int number, String suite) {
        this.number = number;
        this.suite = suite;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Card)) {
            return false;
        }
        Card card = (Card) obj;
        return (number == card.getNumber()) && suite.equals(card.getSuite());
    }


}
