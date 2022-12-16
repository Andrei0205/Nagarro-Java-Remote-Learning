package com.nagarro.remotelearning.app;

import java.util.ArrayList;
import java.util.List;

public class CanvasHolder {
    private List<Canvas> canvas = new ArrayList<>();

    public void addCanvas (Canvas canva) {
        canvas.add(canva);
    }
    public void removeCanvas (int index) {
        canvas.remove(index);
    }
    public void drawAllCanvas() {
        for(Canvas c : canvas) {
            c.drawAllShapes();
        }
    }
}
