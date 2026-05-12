package edu.kis.powp.jobs2d.canvas;

import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.ShapeCommandFactory;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
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
    public ICompoundCommand toCommand() {
        return ShapeCommandFactory.fromCircle(centerX, centerY, radius, margin);
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public void draw(Graphics2D g) {
        Color oldColor = g.getColor();
        Stroke oldStroke = g.getStroke();

        // outer circle
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

        // drawable area
        if (margin > 0) {
            int effectiveRadius = radius - margin;
            g.setColor(Color.GRAY);
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 1f, new float[]{5f, 5f}, 0f));
            g.drawOval(centerX - effectiveRadius, centerY - effectiveRadius,
                    effectiveRadius * 2, effectiveRadius * 2);
        }

        g.setColor(oldColor);
        g.setStroke(oldStroke);
    }
}