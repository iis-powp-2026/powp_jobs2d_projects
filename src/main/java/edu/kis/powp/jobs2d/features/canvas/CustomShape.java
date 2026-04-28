package edu.kis.powp.jobs2d.features.canvas;

public class CustomShape {

    private final int width;
    private final int height;

    public CustomShape(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isValid() {
        return width > 0 && height > 0;
    }
}

