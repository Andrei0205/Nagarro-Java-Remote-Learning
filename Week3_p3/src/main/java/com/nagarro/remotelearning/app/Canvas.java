package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Shape;

import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private String canvasName;
    private List<Shape> shapes = new ArrayList<>();

    public Canvas(String name) {
        this.canvasName = name;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void removeShape(int index) {
        shapes.remove(index);
    }

    public void drawAllShapes() {
        System.out.println("Canvas: " + canvasName + " {");
        for (Shape s : shapes) {
            s.draw();
        }
        System.out.println("Canvas: " + canvasName + "ends }");
    }
}
