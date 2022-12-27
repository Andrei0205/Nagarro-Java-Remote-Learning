package com.nagarro.remotelearning.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SentenceCreator {
    private static final List<String> articles = new ArrayList<>(Arrays.asList("the", "a", "one", "some", "any"));
    private static final List<String> nouns = new ArrayList<>(Arrays.asList("boy", "girl", "dog", "town", "car"));
    private static final List<String> verbs = new ArrayList<>(Arrays.asList("drove", "jumped", "ran", "walked", "skipped"));
    private static final List<String> prepositions = new ArrayList<>(Arrays.asList("to", "from", "over", "under", "on"));
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
        sentence.append(capitalizeFirstLetter(getOneArticle()));
        sentence.append(getOneNoun()).append(BLANK_SPACE);
        sentence.append(getOneVerb()).append(BLANK_SPACE);
        sentence.append(getOnePreposition()).append(BLANK_SPACE);
        sentence.append(getOneArticle()).append(BLANK_SPACE);
        sentence.append(getOneNoun());
        sentence.append(PERIOD);
        return sentence.toString();
    }

    private String capitalizeFirstLetter(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(BLANK_SPACE);
        return stringBuilder.toString();
    }

    private String getOnePreposition() {
        return prepositions.get(random.nextInt(5));
    }

    private String getOneVerb() {
        return verbs.get(random.nextInt(5));
    }

    private String getOneNoun() {
        return nouns.get(random.nextInt(5));
    }

    private String getOneArticle() {
        return articles.get(random.nextInt(5));
    }
}

