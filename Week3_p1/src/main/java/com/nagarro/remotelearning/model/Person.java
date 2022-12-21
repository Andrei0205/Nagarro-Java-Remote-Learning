package com.nagarro.remotelearning.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Person {
    private static final int SURNAME_INDEX = 0;
    protected long age;
    protected String surname;
    protected List<String> firstname = new ArrayList<>();
    protected LocalDate birthDate;
    protected int startIndex = 0;

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

    public abstract LocalDate getBirthDate();

    public abstract void selfDescribe();


    protected List<String> splitName(String fullname) {
        List<String> splittedString = Arrays.asList(fullname.split(" "));
        return splittedString;
    }

    protected void assignFirstNames(List<String> fullnameSplitted) {
        for (int i = startIndex; i < fullnameSplitted.size(); i++) {
            firstname.add(fullnameSplitted.get(i));
        }
    }
    protected String calculateAge() {
        age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        return String.valueOf(age);
    }

}
