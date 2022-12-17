package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class English extends Person {
    private static final int CURRENT_YEAR = 2022;
    private static final int SURNAME_INDEX = 0;
    private static final int BIRTH_DAY_INDEX = 0;
    private static final int BIRTH_MONTH_INDEX = 1;
    private static final int BIRTH_YEAR_INDEX = 2;
    private int startIndex = 0;

    private String surname;
    private List<String> firstname = new ArrayList<>();
    private String birthDate;

    private int birthDay;
    private int birthMonth;
    private int birthYear;


    public English(String surname, String firstname, String birthDate) {
        this.surname = surname;
        assignFirstNames(splitName(firstname));
        this.birthDate = birthDate;
    }

    public English(String fullname, String birthDate) {
        List<String> splittedNames = splitName(fullname);
        this.surname = splittedNames.get(SURNAME_INDEX);
        startIndex += 1;
        assignFirstNames(splittedNames);
        this.birthDate = birthDate;
    }

    @Override
    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public void selfDescribe() {
        extractBirthDateIntoFields(birthDate);
        String age = calculateAge();
        System.out.println("Hello,I'm from England and I like to read. I am " + age + "years old");
    }

    private void extractBirthDateIntoFields(String birthDate) {
        String[] birthDateSplitted = birthDate.split("/");
        birthDay = Integer.parseInt(birthDateSplitted[BIRTH_DAY_INDEX]);
        birthMonth = Integer.parseInt(birthDateSplitted[BIRTH_MONTH_INDEX]);
        birthYear = Integer.parseInt(birthDateSplitted[BIRTH_YEAR_INDEX]);
    }

    private String calculateAge() {
        int age = CURRENT_YEAR - birthYear;
        return String.valueOf(age);
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
}
