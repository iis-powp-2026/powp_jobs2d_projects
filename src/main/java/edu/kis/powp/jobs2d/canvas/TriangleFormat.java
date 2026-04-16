package edu.kis.powp.jobs2d.canvas;

import java.awt.Shape;
import java.awt.geom.Path2D;

public enum TriangleFormat implements CanvasFormat {
    SMALL(200, 200),
    LARGE(400, 400);

    private final int width;
    private final int height;

    TriangleFormat(int base, int height) {
        this.width = base;
        this.height = height;
    }

    @Override
    public Shape getShape() {
        Path2D triangle = new Path2D.Double();
        triangle.moveTo(0, (double) -height / 2);
        triangle.lineTo((double) width / 2, (double) height / 2);
        triangle.lineTo(-(double) width / 2, (double) height / 2);
        triangle.closePath();
        return triangle;
    }

    @Override
    public String getName() {
        return "Triangle Format Canvas (" + width + "x" + height + ")";
    }
}