package com.nagarro.remotelearning.model;

public class Person {
    public static final int SURNAME_INDEX = 0;
    public static final int FIRSTNAME_INDEX = 1;
    private String surname;
    private String firstname;

    public Person(String surname, String firstname) {
        this.surname = surname;
        this.firstname = firstname;
    }

    public Person(String fullname) {
        String[] splittedNames = splitName(fullname);
        assignNames(splittedNames);
    }

    private String[] splitName(String fullname) {
        String[] splittedString = fullname.split(" ", 2);
        return splittedString;
    }

    private void assignNames(String[] fullnameSplitted) {
        this.surname = fullnameSplitted[SURNAME_INDEX];
        this.firstname = fullnameSplitted[FIRSTNAME_INDEX];
    }

    @Override
    public String toString() {
        return "Person{" +
                "surname='" + surname + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }
}
