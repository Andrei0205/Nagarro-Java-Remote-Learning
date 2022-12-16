package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Romanian extends Person {
    private static final int SURNAME_INDEX = 0;
    private int startIndex = 0;

    private String surname;
    private List<String> firstname = new ArrayList<>();


    public Romanian(String surname, String firstname,String birthDate) {
        this.surname = surname;
        assignFirstNames(splitName(firstname));
    }

    public Romanian(String fullname,String birthDate) {
        List<String> splittedNames = splitName(fullname);
        this.surname = splittedNames.get(SURNAME_INDEX);
        startIndex += 1;
        assignFirstNames(splittedNames);

    }

    @Override
    public void getBirthDate() {

    }

    @Override
    public String selfDescribe() {
        return null;
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
