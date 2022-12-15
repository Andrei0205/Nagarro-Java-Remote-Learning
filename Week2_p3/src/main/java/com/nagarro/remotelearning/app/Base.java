package com.nagarro.remotelearning.app;

public class Base {
    private Child classChild = new Child();
    private static int i = initializeInt(3);
    private static int j = initializeInt(30);;
    private static int y;

    private String b = initializeString("Base");
    private String c;

    static {
        y = 3;
        System.out.println("Static block from Base");
    }

    {
        c = "myString c";
        System.out.println("field initialization block from class Base");
    }

    public Base() {
        System.out.println("Constructor from Base");
    }
    private String initializeString(String string) {
        System.out.println("Base class field initialization: " + string);
        return string;
    }

    private static int initializeInt(int i) {
        System.out.println("Base class static field initialization " + i);
        return i;
    }
}
