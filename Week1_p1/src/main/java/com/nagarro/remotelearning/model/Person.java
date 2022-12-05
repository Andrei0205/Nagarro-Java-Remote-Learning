package com.nagarro.remotelearning.model;

public class Person {
    private String firstName;
    private String lastName;
    private String dob;
    private String dod;

    public Person(String firstName, String lastName, String dob, String dod) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.dod = dod;
    }

    public Person(String firstName, String lastName, String dob) {
//        new Person(firstName,lastName,dob,null);   -- Returns Person{null, null, null,null} - why ?
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.dod = null;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + dob + " " + dod;

    }
}
