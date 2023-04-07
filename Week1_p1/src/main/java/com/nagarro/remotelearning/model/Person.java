package com.nagarro.remotelearning.model;

public class Person {
    private final String firstName;
    private final String lastName;
    private final String dob;
    private final String dod;

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
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Person person = (Person) o;
        return this.firstName.equals(person.firstName) && this.lastName.equals(person.lastName);

    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (dob == null ? 0 : dob.hashCode());
        hash = 31 * hash + (dod == null ? 0 : dod.hashCode());
        hash = 31 * hash + (firstName == null ? 0 : firstName.hashCode());
        hash = 31 * hash + (lastName == null ? 0 : lastName.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + dob + " " + dod;

    }
}
