package com.nagarro.remotelearning.utils;


import java.util.Objects;

public class Train {
    private int number;
    private String type;
    private int noWagons;

    public Train(int number, String type, int noWagons) {
        this.number = number;
        this.type = type;
        this.noWagons = noWagons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return number == train.number && noWagons == train.noWagons && type.equals(train.type);

    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type, noWagons);
        //return 3;
    }
}
