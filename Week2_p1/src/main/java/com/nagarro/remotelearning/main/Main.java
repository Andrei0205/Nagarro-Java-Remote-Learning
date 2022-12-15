package com.nagarro.remotelearning.main;


import com.nagarro.remotelearning.model.Person;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("Jhon", "Rhon");
        Person person2 = new Person("Thom Ferris Moris");
        Person person3 = new Person("Thom Ferris Moris Boris");

        System.out.println(person1);
        System.out.println(person2);
        System.out.println(person3);
    }

}