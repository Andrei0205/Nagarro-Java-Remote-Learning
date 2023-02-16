package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.Factory;
import com.nagarro.remotelearning.utils.MyClass;
import com.sun.org.apache.bcel.internal.util.ClassLoader;

import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {

        Factory factory = new Factory();
       Object initial = factory.getClass("initial");
       System.out.println(initial.getClass().getName());
       MyClass myClass = new MyClass();
       System.out.println(myClass.getClass().getName());
       Object reloaded = factory.getClass("reloaded");
       //MyClass subclass = (MyClass) factory.getClass("subclass");
        //MyClass myClass = (MyClass) subclass;

    }
}