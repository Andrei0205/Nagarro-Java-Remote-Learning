package com.nagarro.remotelearning.utils;

public class PigLatinEncoder {

    private static final String PIG_LATIN_SUFFIX = "ay";
    private static final String SPACE_SEPARATOR = " ";
    private static final int SUBSTRING_INDEX = 1;
    private static final int FIRST_LETTER_INDEX = 0;
    private static final String BLANK_SPACE = " ";

    public String encode(String string) {
        String[] splittedString = string.split(SPACE_SEPARATOR);
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : splittedString) {
            stringBuilder.append(transformWordIntoPigLatin(word));
            stringBuilder.append(BLANK_SPACE);
        }
        return stringBuilder.toString();
    }

    private String transformWordIntoPigLatin(String word) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word.substring(SUBSTRING_INDEX));
        stringBuilder.append(word.charAt(FIRST_LETTER_INDEX));
        stringBuilder.append(PIG_LATIN_SUFFIX);
        return stringBuilder.toString();
    }
}
