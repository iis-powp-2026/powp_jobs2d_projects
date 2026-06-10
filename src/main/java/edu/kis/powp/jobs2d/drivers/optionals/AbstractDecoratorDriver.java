package edu.kis.powp.jobs2d.drivers.optionals;

import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

/**
 * Base class for all decorator drivers following the Decorator pattern.
 * Handles target management, pure delegation of setPosition/operateTo,
 * and the visitor accept() dispatch — so subclasses only need to implement
 * toString() and may override setPosition/operateTo to add behaviour.
 */
public abstract class AbstractDecoratorDriver implements DecoratorDriver {

    private VisitableDriver target;

    protected AbstractDecoratorDriver(VisitableDriver target) {
        this.target = target;
    }

    @Override
    public synchronized void setTarget(VisitableDriver target) {
        this.target = target;
    }

    @Override
    public synchronized VisitableDriver getTarget() {
        return target;
    }

    @Override
    public synchronized void setPosition(int x, int y) {
        target.setPosition(x, y);
    }

    @Override
    public synchronized void operateTo(int x, int y) {
        target.operateTo(x, y);
    }

    /**
     * Dispatches to visitor.visit(AbstractDecoratorDriver).
     * All concrete decorator subclasses share this single dispatch point,
     * so adding a new decorator requires no changes to DriverVisitor.
     */
    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}
