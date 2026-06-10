package edu.kis.powp.jobs2d.drivers.optionals;

import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
/**
 * Decorator driver that logs all operations and delegates to an inner driver.
 * Acts as an extension: it can be toggled independently of the selected driver.
 */

public interface DecoratorDriver extends VisitableDriver {
    VisitableDriver getTarget();
    void setTarget(VisitableDriver target);
}
