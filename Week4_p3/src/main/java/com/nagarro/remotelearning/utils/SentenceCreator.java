package com.nagarro.remotelearning.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SentenceCreator {
    private static final List<String> ARTICLES = new ArrayList<>(Arrays.asList("the", "a", "one", "some", "any"));
    private static final List<String> NOUNS = new ArrayList<>(Arrays.asList("boy", "girl", "dog", "town", "car"));
    private static final List<String> VERBS = new ArrayList<>(Arrays.asList("drove", "jumped", "ran", "walked", "skipped"));
    private static final List<String> PREPOSITIONS = new ArrayList<>(Arrays.asList("to", "from", "over", "under", "on"));
    private static final String BLANK_SPACE = " ";
    private static final String PERIOD = ".";
    private Random random = new Random();

    public List<String> createSentences(int numberOfSentences) {
        List<String> sentences = new ArrayList<>();
        for (int i = 0; i < numberOfSentences; i++) {
            sentences.add(createOneSentence());
        }
        return sentences;
    }

    private String createOneSentence() {
        StringBuilder sentence = new StringBuilder();
        sentence.append(capitalizeFirstLetter(getRandomWordFrom(ARTICLES)));
        sentence.append(getRandomWordFrom(NOUNS)).append(BLANK_SPACE);
        sentence.append(getRandomWordFrom(VERBS)).append(BLANK_SPACE);
        sentence.append(getRandomWordFrom(PREPOSITIONS)).append(BLANK_SPACE);
        sentence.append(getRandomWordFrom(ARTICLES)).append(BLANK_SPACE);
        sentence.append(getRandomWordFrom(NOUNS));
        sentence.append(PERIOD);
        return sentence.toString();
    }

    private String capitalizeFirstLetter(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(BLANK_SPACE);
        return stringBuilder.toString();
    }

    private String getRandomWordFrom(List<String> list) {
        return list.get(random.nextInt(5));
    }
}

