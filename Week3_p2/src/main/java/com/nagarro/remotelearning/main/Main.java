package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.app.Amphibian;
import com.nagarro.remotelearning.app.Frog;

public class Main {
    public static void testUpcasting(Amphibian amphibian){
        amphibian.play();
        amphibian.swim("Dale");
    }

    public static void main(String[] args) {
        Frog frog = new Frog();
        testUpcasting(frog);
    }
}