package edu.kis.powp.jobs2d.drivers.bounds;

import java.awt.Point;

public interface MissingCanvasStrategy {

    Point handleMissingCanvas(int x, int y, String driverName);

    default void handleCanvasAvailable(String driverName) {
    }
}
