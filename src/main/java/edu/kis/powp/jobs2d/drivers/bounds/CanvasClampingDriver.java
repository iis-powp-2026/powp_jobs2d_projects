package edu.kis.powp.jobs2d.drivers.bounds;

import java.util.Objects;

import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

public class CanvasClampingDriver implements VisitableDriver {

    private final VisitableDriver innerDriver;
    private final String name;

    public CanvasClampingDriver(VisitableDriver innerDriver, String name) {
        this.innerDriver = Objects.requireNonNull(innerDriver, "innerDriver");
        this.name = Objects.requireNonNull(name, "name");
    }

    @Override
    public void setPosition(int x, int y) {
        innerDriver.setPosition(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        innerDriver.operateTo(x, y);
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
