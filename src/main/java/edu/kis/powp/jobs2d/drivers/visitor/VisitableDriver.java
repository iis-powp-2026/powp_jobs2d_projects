package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.Job2dDriver;

public interface VisitableDriver extends Job2dDriver {
    void accept(DriverVisitor visitor);
}
