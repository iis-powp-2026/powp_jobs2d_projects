package edu.kis.powp.jobs2d.canvas;

import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.ShapeCommandFactory;

/**
 * Circular canvas - example of a non-rectangular ("custom shape") canvas.
 * The drawable area is the disc of radius (radius - margin) centred at
 * (centerX, centerY).
 */
public class CircleCanvas implements ICanvas {

    private final int centerX;
    private final int centerY;
    private final int radius;
    private final int margin;
    private final String name;

    /**
     * @param name    human-readable canvas name
     * @param centerX x-coordinate of the centre
     * @param centerY y-coordinate of the centre
     * @param radius  radius in the same units as the coordinates
     * @param margin  margin reducing the effective drawable radius
     */
    public CircleCanvas(String name, int centerX, int centerY, int radius, int margin) {
        this.name = name;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public boolean contains(int x, int y) {
        int effectiveRadius = radius - margin;
        long dx = x - centerX;
        long dy = y - centerY;
        return dx * dx + dy * dy <= (long) effectiveRadius * effectiveRadius;
    }

    @Override
    public int[] clip(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double relativeX = x1 - centerX, relativeY = y1 - centerY;

        double a = dx*dx + dy*dy;
        double b = 2 * (relativeX * dx + relativeY * dy);
        double c = (relativeX * relativeX) + (relativeY * relativeY) - (radius * radius);

        double delta = b*b - 4*a*c;
        if (delta < 0) return null;

        double deltaSqrt = Math.sqrt(delta);
        double solution1 = (-b - deltaSqrt) / (2*a);
        double solution2 = (-b + deltaSqrt) / (2*a);

        double tEnter = Math.max(0, solution1);
        double tExit = Math.min(1, solution2);

        if (tEnter > tExit) return null;

        return new int[] {
                (int) Math.round(x1 + tEnter * dx),
                (int) Math.round(y1 + tEnter * dy),
                (int) Math.round(x1 + tExit * dx),
                (int) Math.round(y1 + tExit * dy)
        };
    }

    @Override
    public ICompoundCommand toCommand() {
        return ShapeCommandFactory.fromCircle(centerX, centerY, radius, margin);
    }

    @Override
    public String getName() {
        return name;
    }
}