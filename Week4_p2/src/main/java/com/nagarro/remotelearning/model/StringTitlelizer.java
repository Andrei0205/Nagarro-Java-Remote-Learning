package com.nagarro.remotelearning.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringTitlelizer implements Titlelizer {

    private static final List<String> IGNORED_WORDS = new ArrayList<>(Arrays.asList("the", "this", "a", "to", "in", "of", "is"));

    @Override
    public String titlelize(String toTitlelize) {
        if (toTitlelize == null) {
            throw new IllegalArgumentException();
        } else if (toTitlelize.equals("")) {
            System.out.println("Empty string,please enter a valid one");
            return "";
        } //
        String[] splittedString = toTitlelize.split(" ");
        return capitalizeFirstLetterOfEachWord(splittedString);
    }

    private String capitalizeFirstLetterOfEachWord(String[] splittedString) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < splittedString.length; i++) {
            if (isNotIgnoredWord(splittedString[i]) || i == 0) {
                newString.append(Character.toUpperCase(splittedString[i].charAt(0))).append(splittedString[i].substring(1)).append(" ");
            } else {
                newString.append(splittedString[i]).append(" ");
            }
        }
        newString = newString.deleteCharAt(newString.length() - 1);
        return newString.toString();
    }

    private boolean isNotIgnoredWord(String word) {
        for (String s : ignoredWords) {
            if (word.equals(s)) {
                return false;
            }
        }
        return true;
    }

}
