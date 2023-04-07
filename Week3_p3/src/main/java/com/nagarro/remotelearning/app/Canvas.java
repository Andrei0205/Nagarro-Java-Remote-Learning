package com.nagarro.remotelearning.app;

import com.nagarro.remotelearning.model.Drawable;


import java.util.ArrayList;
import java.util.List;

public class Canvas implements Drawable {
    private final String canvasName;
    private final List<Drawable> shapes = new ArrayList<>();

    public Canvas(String name) {
        this.canvasName = name;
    }

    public void addShape(Drawable shape) {
        shapes.add(shape);
    }

    public void removeShape(int index) {
        shapes.remove(index);
    }

    @Override
    public void draw() {
        System.out.println("Canvas: " + canvasName + " {");
        for (Drawable s : shapes) {
            s.draw();
        }
        System.out.println("Canvas: " + canvasName + "ends }");
    }

}
