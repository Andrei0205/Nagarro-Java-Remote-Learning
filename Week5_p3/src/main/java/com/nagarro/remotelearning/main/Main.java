package com.nagarro.remotelearning.main;


import com.nagarro.remotelearning.utils.ClassType;
import com.nagarro.remotelearning.utils.MyClassFactory;
import com.nagarro.remotelearning.utils.MyClass;


import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {

        MyClassFactory factory = new MyClassFactory();
        Object subclass = factory.getClass(ClassType.SUBCLASS);
        MyClass myClass = (MyClass) subclass;
    }
}