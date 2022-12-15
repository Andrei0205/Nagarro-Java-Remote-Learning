package com.nagarro.remotelearning.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Person {
    private static final int SURNAME_INDEX = 0;
    private int startIndex = 0;
    private String surname;
    private List<String> firstname = new ArrayList<>();

    public Person(String surname, String firstname) {
        this.surname = surname;
        assignFirstNames(splitName(firstname));
    }

    public Person(String fullname) {
        List<String> splittedNames = splitName(fullname);
        this.surname = splittedNames.get(SURNAME_INDEX);
        startIndex += 1;
        assignFirstNames(splittedNames);
    }

    private List<String> splitName(String fullname) {
        List<String> splittedString = Arrays.asList(fullname.split(" "));
        return splittedString;
    }

    private void assignFirstNames(List<String> fullnameSplitted) {
        for (int i = startIndex; i < fullnameSplitted.size(); i++) {
            firstname.add(fullnameSplitted.get(i));
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "surname='" + surname + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }
}
