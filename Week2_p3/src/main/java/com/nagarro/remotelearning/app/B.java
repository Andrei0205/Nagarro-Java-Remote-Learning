package com.nagarro.remotelearning.app;

public class B extends C {
    private static int i;
    private String b;

    static {
        i = 4;
        System.out.println("Static block from B");
    }

    {
        b = "myString b";
        System.out.println("field initialization from class B");
    }

    B() {
        System.out.println("Constructor from B");
    }
}
