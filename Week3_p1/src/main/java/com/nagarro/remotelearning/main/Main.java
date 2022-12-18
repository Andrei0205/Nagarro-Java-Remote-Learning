package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.app.English;
import com.nagarro.remotelearning.app.Romanian;
import com.nagarro.remotelearning.model.Person;

public class Main {
    public static void main(String[] args) {
        Person romanian = new Romanian("Marinescu Andrei", "20/01/2000");
        Person english = new English("Thomas Edison","14/11/1990");
        english.selfDescribe();
        romanian.selfDescribe();

    }
}