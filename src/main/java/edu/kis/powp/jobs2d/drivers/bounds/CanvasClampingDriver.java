package edu.kis.powp.jobs2d.drivers.bounds;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Logger;

import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

public class CanvasClampingDriver implements VisitableDriver {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final VisitableDriver innerDriver;
    private final Supplier<ICanvas> canvasSupplier;
    private final String name;
    private boolean loggedMissingCanvas;

    public CanvasClampingDriver(VisitableDriver innerDriver, Supplier<ICanvas> canvasSupplier, String name) {
        this.innerDriver = Objects.requireNonNull(innerDriver, "innerDriver");
        this.canvasSupplier = Objects.requireNonNull(canvasSupplier, "canvasSupplier");
        this.name = Objects.requireNonNull(name, "name");
    }

    @Override
    public void setPosition(int x, int y) {
        int[] p = clamp(x, y);
        innerDriver.setPosition(p[0], p[1]);
    }

    @Override
    public void operateTo(int x, int y) {
        int[] p = clamp(x, y);
        innerDriver.operateTo(p[0], p[1]);
    }

    private int[] clamp(int x, int y) {
        ICanvas canvas = canvasSupplier.get();
        if (canvas == null) {
            if (!loggedMissingCanvas) {
                logger.info("CanvasClampingDriver (\"" + name + "\"): no canvas format selected — "
                        + "coordinates pass through without clamping. Choose a format in the Canvas menu.");
                loggedMissingCanvas = true;
            }
            return new int[] { x, y };
        }
        loggedMissingCanvas = false;
        return canvas.clampToBounds(x, y);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }

    public VisitableDriver getInnerDriver() {
        return innerDriver;
    }
}
