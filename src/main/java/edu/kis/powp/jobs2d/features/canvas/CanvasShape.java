package edu.kis.powp.jobs2d.features.canvas;

public interface CanvasShape {

    boolean isValid();

    CanvasLine[] getGuideLines();

    String describe();
}

