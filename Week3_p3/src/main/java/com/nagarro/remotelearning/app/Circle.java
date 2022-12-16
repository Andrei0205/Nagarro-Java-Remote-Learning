package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Shape;

public class Circle implements Shape {
    private Point centerPoint;
    private Point pointOfTheCircle;
    private Line radius;

    public Circle(Point centerPoint, Point pointOfTheCircle) {
        this.centerPoint = centerPoint;
        this.pointOfTheCircle = pointOfTheCircle;
        createRadius(centerPoint,pointOfTheCircle);
    }

    @Override
    public void draw() {
        System.out.println("Circle with:");
        System.out.print("Center: ");
        centerPoint.draw();
        System.out.print("And radius: ");
        radius.draw();
        System.out.println("Circle ends");
    }

    @Override
    public void erase() {

    }

    private void createRadius(Point centerPoint, Point pointOfTheCircle){
        radius = new Line(centerPoint,pointOfTheCircle);
    }
}
