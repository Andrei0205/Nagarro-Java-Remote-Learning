package com.nagarro.remotelearning.factory;

import com.nagarro.remotelearning.handlers.DynamicProxyHandler;
import com.nagarro.remotelearning.model.Drawable;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    public static Drawable getProxy(Object object) {
        return (Drawable) Proxy.newProxyInstance(
                Drawable.class.getClassLoader(),
                new Class[]{Drawable.class},
                new DynamicProxyHandler(object));
    }

}
