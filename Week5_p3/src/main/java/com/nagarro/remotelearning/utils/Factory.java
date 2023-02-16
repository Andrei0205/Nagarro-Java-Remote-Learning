package com.nagarro.remotelearning.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Factory {

    public Object getClass(String type) throws MalformedURLException, ClassNotFoundException {
        if(type == null ) {
            return null;
        }
        if(type.equalsIgnoreCase("initial")){
            return new MyClass();
        }
        if(type.equalsIgnoreCase("reloaded")) {
            File file = new File("D:\\DynamicClass\\target\\classes");

            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader classLoader = new URLClassLoader(urls);

            Class myClass = classLoader.loadClass("org.example.utils.MyClass");
            return myClass;
        } if(type.equalsIgnoreCase("subclass")) {
            File file = new File("D:\\DynamicClass\\target\\classes");

            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader classLoader = new URLClassLoader(urls);

            Class myClass = classLoader.loadClass("org.example.utils.SubClass");
            return myClass;
        }
        return null;
    }

}
