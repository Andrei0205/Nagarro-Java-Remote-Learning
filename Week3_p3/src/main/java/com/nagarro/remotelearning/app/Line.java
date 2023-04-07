package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Drawable;

public class Line implements Drawable {

    private final Point startCoordinate;
    private final Point endCoordinate;

    public Line(Point startCoordinate, Point endCoordinate) {
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
    }

    @Override
    public void draw() {
        System.out.println("Line from: " + startCoordinate.toString() + " to " + endCoordinate.toString());
    }

}
