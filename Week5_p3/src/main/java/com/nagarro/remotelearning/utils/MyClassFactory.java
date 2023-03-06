package com.nagarro.remotelearning.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassFactory {

    private static final String D_DYNAMIC_CLASS_TARGET_CLASSES = "D:\\DynamicClass\\target\\classes";

    public MyClass getClass(ClassType type) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        switch (type) {
            case INITIAL:
                return new MyClass();
            case RELOADED:
                return loadClass(type.getClassType());
            case SUBCLASS:
                return loadClass(type.getClassType());
            default:
                throw new UnsupportedOperationException("Please select existing type");
        }

    }

    private  MyClass loadClass(String classType) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        File file = new File(D_DYNAMIC_CLASS_TARGET_CLASSES);

        URL url = file.toURI().toURL();
        URL[] urls = new URL[]{url};

        ClassLoader classLoader = new URLClassLoader(urls);

        Class myClass = classLoader.loadClass(classType);
        return (MyClass) myClass.newInstance();
    }

}
