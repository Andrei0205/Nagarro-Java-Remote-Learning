package com.nagarro.remotelearning.handlers;

import com.nagarro.remotelearning.annotations.Logged;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DynamicProxyHandler implements InvocationHandler {
    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (proxied.getClass().isAnnotationPresent(Logged.class)) {
            System.out.println("*** class: " + proxy.getClass().getName() + " type: " + proxied.getClass().getTypeName());
        }

        if (method.isAnnotationPresent(Logged.class)) {
            System.out.println("**** proxy: " + proxy.getClass().getName() + ", method: " + method + ", args: " + Arrays.toString(args) + " " +
                    proxy.getClass().getTypeName());
        }
        return method.invoke(proxied, args);
    }
}