package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.features.CanvasFeature;

import java.awt.*;

public class BoundsDriver implements VisitableDriver {

    private final VisitableDriver innerDriver;
    private int virtualX, virtualY;
    private int x, y;

    public BoundsDriver(VisitableDriver innerDriver) {
        this.innerDriver = innerDriver;
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void setPosition(int nx, int ny) {
        virtualX = nx;
        virtualY = ny;
    }

    @Override
    public void operateTo(int nx, int ny) {
        ICanvas canvas = CanvasFeature.getCanvas();
        int[] clipped = canvas.clip(virtualX, virtualY, nx, ny);

        if (clipped != null) {
            moveTo(clipped[0], clipped[1]);
            innerDriver.operateTo(clipped[2], clipped[3]);
            x = clipped[2];
            y = clipped[3];
        }

        virtualX = nx;
        virtualY = ny;
        moveTo(nx, ny);
    }

    private void moveTo(int nx, int ny) {
        if (nx != x || ny != y) {
            innerDriver.setPosition(nx, ny);
            x = nx;
            y = ny;
        }
    }

    public VisitableDriver getInnerDriver() {
        return innerDriver;
    }
}
