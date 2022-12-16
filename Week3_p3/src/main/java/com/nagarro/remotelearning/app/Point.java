package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Shape;

public class Point implements Shape {
    private double coordinate;

    public Point(double coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public void draw() {
        System.out.println("Point: " + coordinate);
    }

    @Override
    public void erase() {

    }

    @Override
    public String toString() {
        return  "" + coordinate;
    }
}
