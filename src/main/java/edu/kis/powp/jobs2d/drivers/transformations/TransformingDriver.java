package edu.kis.powp.jobs2d.drivers.transformations;


import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

import edu.kis.powp.jobs2d.drivers.DriverDecorator;

public class TransformingDriver implements DriverDecorator {
    private VisitableDriver innerDriver;
    private final CoordinateTransformer transformer;
    private final String name;

    public TransformingDriver(VisitableDriver innerDriver, CoordinateTransformer transformer, String name) {
        this.innerDriver = innerDriver;
        this.transformer = transformer;
        this.name = name;
    }

    @Override
    public void setPosition(int x, int y) {
        int[] newCoords = transformer.transform(x, y);
        innerDriver.setPosition(newCoords[0], newCoords[1]);
    }

    @Override
    public void operateTo(int x, int y) {
        int[] newCoords = transformer.transform(x, y);
        innerDriver.operateTo(newCoords[0], newCoords[1]);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public VisitableDriver getInnerDriver() {
        return innerDriver;
    }

    @Override
    public void setInnerDriver(VisitableDriver driver) {
        this.innerDriver = driver;
    }
}