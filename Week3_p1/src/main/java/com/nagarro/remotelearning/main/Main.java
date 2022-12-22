package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.app.English;
import com.nagarro.remotelearning.app.Romanian;
import com.nagarro.remotelearning.model.Person;


public class Main {
    public static void main(String[] args) {
        Person romanian = new Romanian("Marinescu", "Andrei Ionut ", "20-01-1992");
        Person english = new English("Jhon Ferris Anton ", "2001-02-05");
        english.selfDescribe();
        romanian.selfDescribe();
        System.out.println(romanian.getBirthDate());
        //test for sourcetree
    }
}