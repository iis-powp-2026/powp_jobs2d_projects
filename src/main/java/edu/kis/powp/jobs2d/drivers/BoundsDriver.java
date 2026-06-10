package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.drivers.optionals.AbstractDecoratorDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.features.CanvasFeature;

public class BoundsDriver extends AbstractDecoratorDriver {

    private int virtualX, virtualY;
    private int x, y;

    public BoundsDriver(VisitableDriver innerDriver) {
        super(innerDriver);
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
            getTarget().operateTo(clipped[2], clipped[3]);
            x = clipped[2];
            y = clipped[3];
        }

        virtualX = nx;
        virtualY = ny;
        moveTo(nx, ny);
    }

    private void moveTo(int nx, int ny) {
        if (nx != x || ny != y) {
            getTarget().setPosition(nx, ny);
            x = nx;
            y = ny;
        }
    }

    @Override
    public String toString() {
        return "BoundsDriver -> " + getTarget();
    }
}
