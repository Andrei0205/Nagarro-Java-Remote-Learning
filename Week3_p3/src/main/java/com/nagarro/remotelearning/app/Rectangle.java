package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Drawable;


public class Rectangle implements Drawable {
    private Point A;
    private Point B;
    private Point C;
    private Point D;
    private Line AB;
    private Line BC;
    private Line CD;
    private Line DA;


    public Rectangle(Point A, Point B, Point C, Point D) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        drawLines(A, B, C, D);
    }

    @Override
    public void draw() {
        System.out.println("Rectangle: ");
        System.out.println("Points: ");
        A.draw();
        B.draw();
        C.draw();
        D.draw();
        System.out.println("Lines: ");
        AB.draw();
        BC.draw();
        CD.draw();
        DA.draw();
        System.out.println("Rectangle ends");

    }

    private void drawLines(Point A, Point B, Point C, Point D) {
        AB = new Line(A, B);
        BC = new Line(B, C);
        CD = new Line(C, D);
        DA = new Line(D, A);
    }
}
