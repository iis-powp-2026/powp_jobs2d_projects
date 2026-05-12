package edu.kis.powp.jobs2d.canvas;

import java.awt.Graphics2D;

import edu.kis.powp.jobs2d.command.ICompoundCommand;

/**
 * Represents a drawing area (canvas) with optional margin.
 * Implementations define the geometry of the drawable region.
 */
public interface ICanvas {

    /**
     * Check whether the given point lies within the drawable area
     * (i.e. inside the canvas and not within the margin).
     */
    boolean contains(int x, int y);

    /**
     * Human-readable name of this canvas.
     */
    String getName();

    /**
     * Draw the outline of this canvas onto the given Graphics2D context.
     */
    void draw(Graphics2D g);

    /**
     * Returns a compound command that, when executed by a driver,
     * draws the outline of this canvas.
     */
    ICompoundCommand toCommand();
}