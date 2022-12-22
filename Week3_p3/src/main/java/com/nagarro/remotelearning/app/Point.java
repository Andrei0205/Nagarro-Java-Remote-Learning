package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Drawable;

public class Point implements Drawable {
    private double coordinateX;
    private double coordinateY;

    public Point(double coordinateX, double coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    @Override
    public void draw() {
        System.out.println("Point: " + "X: " + coordinateX + " Y: " + coordinateY);
    }

    @Override
    public String toString() {
        return "" + "X: " + coordinateX + " Y: " + coordinateY;
    }
}
