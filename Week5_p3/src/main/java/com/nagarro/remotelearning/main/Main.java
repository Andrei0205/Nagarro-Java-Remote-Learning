package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.utils.Factory;
import com.nagarro.remotelearning.utils.MyClass;
import com.sun.org.apache.bcel.internal.util.ClassLoader;

import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {

        Factory factory = new Factory();
       Object initial = factory.getClass("initial");
       Object reloaded = factory.getClass("reloaded");
       MyClass subclass = (MyClass) factory.getClass("subclass");
        //MyClass myClass = (MyClass) subclass;

    }
}