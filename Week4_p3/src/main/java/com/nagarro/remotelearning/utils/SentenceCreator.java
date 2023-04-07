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
    private final Random random = new Random();

    public List<String> createSentences(int numberOfSentences) {
        List<String> sentences = new ArrayList<>();
        for (int i = 0; i < numberOfSentences; i++) {
            sentences.add(createOneSentence());
        }
        return sentences;
    }

    private String createOneSentence() {
        String sentence = capitalizeFirstLetter(getRandomWordFrom(ARTICLES)) +
                getRandomWordFrom(NOUNS) + BLANK_SPACE +
                getRandomWordFrom(VERBS) + BLANK_SPACE +
                getRandomWordFrom(PREPOSITIONS) + BLANK_SPACE +
                getRandomWordFrom(ARTICLES) + BLANK_SPACE +
                getRandomWordFrom(NOUNS) +
                PERIOD;
        return sentence;
    }

    private String capitalizeFirstLetter(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1) + BLANK_SPACE;
    }

    private String getRandomWordFrom(List<String> list) {
        return list.get(random.nextInt(5));
    }
}

