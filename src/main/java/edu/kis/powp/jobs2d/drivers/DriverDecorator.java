package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

public interface DriverDecorator extends VisitableDriver {
    void setInnerDriver(VisitableDriver driver);
    VisitableDriver getInnerDriver();
}
