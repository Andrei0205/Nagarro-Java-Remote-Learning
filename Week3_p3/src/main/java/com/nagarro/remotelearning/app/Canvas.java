package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Shape;

import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private List<Shape> shapes = new ArrayList<>();

    public void addShape (Shape shape) {
        shapes.add(shape);
    }
    public void removeShape(int index) {
        shapes.remove(index);
    }
    public void drawAllShapes() {
        for(Shape s : shapes) {
        s.draw();
        }
    }
}
