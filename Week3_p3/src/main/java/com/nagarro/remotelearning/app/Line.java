package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Shape;

public class Line implements Shape {

    private Point startCoordinate;
    private Point endCoordinate;

    public Line(Point startCoordinate, Point endCoordinate) {
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
    }

    @Override
    public void draw() {
        System.out.println("Line from: " + startCoordinate.toString() + " to " + endCoordinate.toString());
    }

    @Override
    public void erase() {

    }
}
