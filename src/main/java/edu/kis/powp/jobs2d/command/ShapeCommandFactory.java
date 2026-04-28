package edu.kis.powp.jobs2d.command;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

public class ShapeCommandFactory {
    private static ICompoundCommand fromShape(Shape shape) {
        SimpleComplexCommandBuilder pathBuilder = new SimpleComplexCommandBuilder();

        PathIterator segments = shape.getPathIterator(null);

        double[] coordinates = new double[2];

        int startX = 0;
        int startY = 0;

        while (!segments.isDone()) {
            int segmentType = segments.currentSegment(coordinates);

            int destinationX = (int) coordinates[0];
            int destinationY = (int) coordinates[1];

            if (segmentType == PathIterator.SEG_MOVETO) {
                startX = destinationX;
                startY = destinationY;
                pathBuilder.setPosition(destinationX, destinationY);
            } else if (segmentType == PathIterator.SEG_LINETO) {
                pathBuilder.operateTo(destinationX, destinationY);
            } else {
                pathBuilder.operateTo(startX, startY);
            }

            segments.next();
        }

        return pathBuilder.build();
    }

    public static ICompoundCommand fromRectangle(int width, int height, int margin) {
        Shape rectangle =  new Rectangle2D.Double(
                (double) -width / 2,
                (double) -height / 2,
                width - 2.0 * margin,
                height - 2.0 * margin
        );

        return fromShape(rectangle);
    }

    public static ICompoundCommand fromCircle(int centerX, int centerY, int radius, int margin) {
        int effectiveRadius = radius - margin;

        Shape circle = new Ellipse2D.Double(
                centerX - effectiveRadius,
                centerY - effectiveRadius,
                2.0 * effectiveRadius,
                2.0 * effectiveRadius
        );

        return fromShape(circle);
    }
}
