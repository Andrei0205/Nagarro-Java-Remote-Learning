package com.nagarro.remotelearning.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Factory {

    public MyClass getClass(Classes type) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        switch (type) {
            case INITIAL:
                return new MyClass();
            case RELOADED:
                File file = new File("D:\\DynamicClass\\target\\classes");

                URL url = file.toURI().toURL();
                URL[] urls = new URL[]{url};

                ClassLoader classLoader = new URLClassLoader(urls);

                Class myClass = classLoader.loadClass("com.nagarro.remotelearning.utils.MyClass");
                return (MyClass) myClass.newInstance();
            case SUBCLASS:
                 file = new File("D:\\DynamicClass\\target\\classes");

                 url = file.toURI().toURL();
                 urls = new URL[]{url};

                 classLoader = new URLClassLoader(urls);

                 myClass = classLoader.loadClass("com.nagarro.remotelearning.utils.SubClass");
                return (MyClass) myClass.newInstance();
            default:
                return null;
        }

    }

}
