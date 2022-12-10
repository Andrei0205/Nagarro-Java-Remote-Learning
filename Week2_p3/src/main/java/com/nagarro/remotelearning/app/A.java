package com.nagarro.remotelearning.app;

public class A {
    private B classB = new B();
    private static int i;
    private String a;

    static {
        i = 3;
        System.out.println("Static block from A");
    }

    {
        a = "myString a";
        System.out.println("field initialization from class A");
    }

    public A() {
        System.out.println("Constructor from A");
    }
}
