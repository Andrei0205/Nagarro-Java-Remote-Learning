package com.nagarro.remotelearning.app;

public class Parent {
    private static final int i = initializeInt(1);
    private static final int j = initializeInt(10);

    private static final int y;

    private final String b = initializeString("Parent");

    private final String c;

    static {
        y = 2;
        System.out.println("Static block from Parent");
    }

    {
        c = "myString c";
        System.out.println("field initialization block  from class Parent");
    }

    Parent() {
        System.out.println("Constructor from Parent");
    }

    private String initializeString(String string) {
        System.out.println("Parent class field initialization: " + string);
        return string;
    }

    private static int initializeInt(int i) {
        System.out.println("Parent class static field initialization " + i);
        return i;
    }
}

