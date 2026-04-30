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
     * @return CompoundCommand which draws the canvas guides
     */
    ICompoundCommand toCommand();

    /**
     * @return human-readable name of this canvas (e.g. "A4", "B3", "Circle r=200")
     */
    String getName();
}