package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.app.*;

public class Main {
    public static void main(String[] args) {
        Point A = new Point(2.0);
        Point B = new Point(3.4);
        Point C = new Point(8.3);
        Point D = new Point(10.1);
        Point E = new Point(7.0);
        Point F = new Point(12.4);
        Point G = new Point(18.3);
        Point H = new Point(22.1);
        Line AB = new Line(A, B);
        Line FG = new Line(F, G);
        Rectangle rectangle1 = new Rectangle(A, B, C, D);
        Rectangle rectangle2 = new Rectangle(E, F, G, H);
        Circle circle1 = new Circle(A, B);
        Circle circle2 = new Circle(E, F);
        Canvas canvas20x20 = new Canvas("20x20");
        Canvas canvas30x40 = new Canvas("30x40");

        canvas20x20.addShape(A);
        canvas20x20.addShape(AB);
        canvas20x20.addShape(rectangle1);
        canvas20x20.addShape(circle1);

        canvas30x40.addShape(E);
        canvas30x40.addShape(FG);
        canvas30x40.addShape(rectangle2);
        canvas30x40.addShape(circle2);

        CanvasHolder canvasHolder = new CanvasHolder();
        canvasHolder.addCanvas(canvas20x20);
        canvasHolder.addCanvas(canvas30x40);
        canvasHolder.drawAllCanvas();

    }
}