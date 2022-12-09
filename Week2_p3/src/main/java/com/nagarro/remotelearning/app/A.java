package com.nagarro.remotelearning.app;

public class A {
    B classB = new B();
    static int i;
    String a;

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
