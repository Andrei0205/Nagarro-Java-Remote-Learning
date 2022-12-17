package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.app.Romanian;
import com.nagarro.remotelearning.model.Person;

public class Main {
    public static void main(String[] args) {
        Person roman = new Romanian("Marinescu Andrei", "20/01/2000");
        roman.selfDescribe();
        System.out.println(roman.getBirthDate());
    }
}