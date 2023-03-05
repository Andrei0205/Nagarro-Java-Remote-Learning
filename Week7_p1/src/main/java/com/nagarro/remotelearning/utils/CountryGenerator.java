package com.nagarro.remotelearning.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class CountryGenerator {
    private final List<String> countryNames = Arrays.asList("Romania", "Bulgaria", "Franta", "Ungaria", "Spania", "Italia");
    private final List<String> countryCapitals = Arrays.asList("Bucuresti", "Sofia", "Paris", "Budapesta", "Madrid", "Roma");
    private Random random = new Random();

    public List<Country> generateRandom(int noOfCountries) {
        List<Country> countries = new ArrayList<>();
        int randomIndex;
        for (int index = 1; index <= noOfCountries; index++) {
            randomIndex = random.nextInt(countryNames.size());
            countries.add(new Country(countryNames.get(randomIndex), countryCapitals.get(randomIndex)));
        }
        return countries;
    }


}
