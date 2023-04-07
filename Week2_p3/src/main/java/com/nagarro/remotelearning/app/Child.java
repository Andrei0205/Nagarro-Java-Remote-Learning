package com.nagarro.remotelearning.app;

public class Child extends Parent {
    private static final int i = initializeInt(2);
    private static final int j = initializeInt(20);
    private static final int y;
    private final String b = initializeString("child");
    private final String c;

    static {
        y = 4;
        System.out.println("Static block from Child");
    }

    {
        c = "myString c";
        System.out.println("field initialization block from class Child");
    }

    Child() {
        System.out.println("Constructor from Child");
    }
    private String initializeString(String string) {
        System.out.println("Child class field initialization: " + string);
        return string;
    }

    private static int initializeInt(int i) {
        System.out.println("Child class static field initialization " + i);
        return i;
    }
}
