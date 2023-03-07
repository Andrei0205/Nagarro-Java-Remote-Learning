package com.nagarro.remotelearning.utils;

import java.util.Objects;

public class Train {
    private final int number;
    private final String type;
    private final int noWagons;

    public Train(int number, String type, int noWagons) {
        this.number = number;
        this.type = type;
        this.noWagons = noWagons;
    }

    @Override
    public boolean equals(Object anotherTrain) {
        if (this == anotherTrain) return true;
        if (anotherTrain == null || getClass() != anotherTrain.getClass()) return false;
        Train train = (Train) anotherTrain;
        return number == train.number && noWagons == train.noWagons && type.equals(train.type);

    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type, noWagons);
    }
}
