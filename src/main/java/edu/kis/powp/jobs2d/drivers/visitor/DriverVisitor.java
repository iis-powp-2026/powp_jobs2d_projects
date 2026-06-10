package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.optionals.AbstractDecoratorDriver;
import edu.kis.powp.jobs2d.drivers.optionals.TrackingLoggerDriver;
import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;

public interface DriverVisitor {
    void visit(CompositeDriver driver);
    void visit(LineDriverAdapter adapter);
    void visit(TrackingLoggerDriver driver);
    void visit(AbstractDecoratorDriver driver);
}
