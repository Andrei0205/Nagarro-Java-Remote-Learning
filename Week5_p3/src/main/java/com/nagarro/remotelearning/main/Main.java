package com.nagarro.remotelearning.main;


import com.nagarro.remotelearning.utils.Classes;
import com.nagarro.remotelearning.utils.Factory;
import com.nagarro.remotelearning.utils.MyClass;


import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {

        Factory factory = new Factory();
        Object subclass = factory.getClass(Classes.SUBCLASS);
        MyClass myClass = (MyClass) subclass;
    }
}