package com.nagarro.remotelearning.app;

public class C {
    private static int i;
    private String c;

    static {
        i = 2;
        System.out.println("Static block from C");
    }

    {
        c = "myString c";
        System.out.println("field initialization from class C");
    }

    C() {
        System.out.println("Constructor from C");
    }
}

