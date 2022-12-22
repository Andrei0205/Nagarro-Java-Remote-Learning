package com.nagarro.remotelearning.main;

import com.nagarro.remotelearning.app.*;

public class Main {
    public static void main(String[] args) {
        Point A = new Point(2.0, 1.0);
        Point B = new Point(3.4, 2.0);
        Point C = new Point(8.3, 5.4);
        Point D = new Point(10.1, 10);
        Point E = new Point(7.0, -2.3);
        Point F = new Point(12.4, -4.5);
        Point G = new Point(18.3, 14);
        Point H = new Point(22.1, 9.3);
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

        canvas20x20.addShape(canvas30x40);

        canvas20x20.draw();

    }
}