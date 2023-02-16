package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.factory.ProxyFactory;
import com.nagarro.remotelearning.model.Drawable;
import com.nagarro.remotelearning.model.Rectangle;


public class Main {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        Drawable proxy = ProxyFactory.getProxy(rectangle);
        proxy.draw();

    }
}