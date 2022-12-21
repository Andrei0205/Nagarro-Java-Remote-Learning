package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.app.Amphibian;
import com.nagarro.remotelearning.app.Frog;

public class Main {

    public static void main(String[] args) {

        System.out.println("Before upcasting:");
        Frog frog = new Frog();
        frog.play();
        frog.swim("Dale");

        System.out.println("After upcasting:");
        Amphibian frog2 = new Frog();
        frog2.play();
        frog2.swim("Chip");
    }
}