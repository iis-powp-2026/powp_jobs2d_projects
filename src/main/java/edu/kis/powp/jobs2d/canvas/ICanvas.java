package edu.kis.powp.jobs2d.canvas;

import edu.kis.powp.jobs2d.command.ICompoundCommand;

/**
 * Represents a drawing area (canvas) with optional margin.
 * Implementations define the geometry of the drawable region.
 */
public interface ICanvas {

    /**
     * Check whether the given point lies within the drawable area
     * (i.e. inside the canvas and not within the margin).
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if the point is within bounds, false if it exceeds them
     */
    boolean contains(int x, int y);

    /**
     * Clips the line
     * @param x1,y1 first point
     * @param x2,y2 second point
     * @return New line with clipped coordinates
     */
    int[] clip(int x1, int y1, int x2, int y2);

    /**
     * @return CompoundCommand which draws the canvas guides
     */
    ICompoundCommand toCommand();

    /**
     * @return human-readable name of this canvas (e.g. "A4", "B3", "Circle r=200")
     */
    String getName();
}