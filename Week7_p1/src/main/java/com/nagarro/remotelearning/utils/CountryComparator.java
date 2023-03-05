package com.nagarro.remotelearning.utils;

import java.util.Comparator;

public class CountryComparator implements Comparator<Country> {
    @Override
    public int compare(Country o1, Country o2) {
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getCapital(), o2.getCapital());
    }
}
