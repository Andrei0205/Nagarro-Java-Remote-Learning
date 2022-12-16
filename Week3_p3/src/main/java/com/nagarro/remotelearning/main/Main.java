package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.app.*;

public class Main {
    public static void main(String[] args) {
        Point A = new Point(2.0);
        Point B = new Point(3.4);
        Point C = new Point(8.3);
        Point D = new Point(10.1);
        Line AB = new Line(A, B);
        Rectangle rectangle = new Rectangle(A,B,C,D);
        Circle circle = new Circle(A,B);
        Canvas canvas = new Canvas();
        canvas.addShape(circle);
        canvas.drawAllShapes();
    }
}