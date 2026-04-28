package edu.kis.powp.jobs2d.features.canvas;

public class CustomShape implements CanvasShape {

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

    @Override
    public boolean isValid() {
        return width > 0 && height > 0;
    }

    @Override
    public CanvasLine[] getGuideLines() {
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        return new CanvasLine[] {
                new CanvasLine(-halfWidth, -halfHeight, halfWidth, -halfHeight),
                new CanvasLine(halfWidth, -halfHeight, halfWidth, halfHeight),
                new CanvasLine(halfWidth, halfHeight, -halfWidth, halfHeight),
                new CanvasLine(-halfWidth, halfHeight, -halfWidth, -halfHeight)
        };
    }

    @Override
    public String describe() {
        return width + "x" + height;
    }
}

