package edu.kis.powp.jobs2d.canvas;

import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.ShapeCommandFactory;

import java.awt.*;

/**
 * Rectangular canvas with optional margin. The drawable area is a rectangle
 * centred at the origin with the specified width and height, shrunk on all
 * sides by the margin.
 *
 * Coordinates are treated as in screen-style (origin at top-left of the
 * drawable area: x..x+width on X, y..y+height on Y).
 */
public class RectangleCanvas implements ICanvas {

    private final Rectangle innerBounds;
    private final int margin;
    private final String name;

    /**
     * @param name   human-readable canvas name (e.g. "A4")
     * @param width  width in pixels (or generic units)
     * @param height height in the same units as width
     * @param margin margin applied on every side, in the same units
     */
    public RectangleCanvas(String name, int width, int height, int margin) {
        this.innerBounds = new Rectangle(-width/2 + margin, -height/2 + margin, width - margin, height - margin);
        this.name = name;
        this.margin = margin;
    }

    @Override
    public boolean contains(int x, int y) {
        return innerBounds.contains(x, y);
    }

    @Override
    public int[] clip(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1, dy = y2 - y1;

        int minX = innerBounds.x, minY = innerBounds.y;
        int maxX = innerBounds.x + innerBounds.width, maxY = innerBounds.y + innerBounds.height;

        double[] p = { -dx, dx, -dy, dy };
        double[] q = { x1 - minX, maxX - x1, y1 - minY, maxY - y1 };

        double tEnter = 0, tExit = 1;

        for (int i = 0; i < 4; i++) {
            if (p[i] == 0) {
                if (q[i] < 0) return null;
            } else {
                double t = q[i] / p[i];
                if (p[i] < 0) {
                    if (t > tEnter) tEnter = t;
                } else {
                    if (t < tExit) tExit = t;
                }
            }
        }

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
        return ShapeCommandFactory.fromShape(innerBounds);
    }

    @Override
    public String getName() {
        return name;
    }
}