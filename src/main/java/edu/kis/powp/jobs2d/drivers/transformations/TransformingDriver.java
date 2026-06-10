package edu.kis.powp.jobs2d.drivers.transformations;

import edu.kis.powp.jobs2d.drivers.optionals.AbstractDecoratorDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

public class TransformingDriver extends AbstractDecoratorDriver {
    private final CoordinateTransformer transformer;
    private final String name;

    public TransformingDriver(VisitableDriver innerDriver, CoordinateTransformer transformer, String name) {
        super(innerDriver);
        this.transformer = transformer;
        this.name = name;
    }

    @Override
    public void setPosition(int x, int y) {
        int[] newCoords = transformer.transform(x, y);
        getTarget().setPosition(newCoords[0], newCoords[1]);
    }

    @Override
    public void operateTo(int x, int y) {
        int[] newCoords = transformer.transform(x, y);
        getTarget().operateTo(newCoords[0], newCoords[1]);
    }

    @Override
    public String toString() {
        return name;
    }
}