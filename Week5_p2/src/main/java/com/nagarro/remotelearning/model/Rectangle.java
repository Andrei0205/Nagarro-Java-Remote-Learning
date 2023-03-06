package com.nagarro.remotelearning.model;

import com.nagarro.remotelearning.annotations.Logged;
@Logged
public class Rectangle implements Drawable {
    @Logged
    public void draw() {
        System.out.println("Rectangle");
    }

}
