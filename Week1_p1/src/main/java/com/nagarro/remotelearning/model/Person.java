package com.nagarro.remotelearning.model;

public class Person {
    private String firstName;
    private String lastName;
    private String dob;
    private String dod = null;

    public Person(String line) {
        String[] split = line.split(",");
        firstName = split[0];
        lastName = split[1];
        dob = split[2];
        if (split.length < 4) {
            dod = "Still alive";
        } else {
            dod = split[3];
        }

    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + dob + " " + dod;

    }
}
