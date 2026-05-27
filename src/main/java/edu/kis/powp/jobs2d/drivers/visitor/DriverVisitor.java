package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.drivers.BoundsDriver;
import edu.kis.powp.jobs2d.drivers.RealTimeDriver;
import edu.kis.powp.jobs2d.drivers.RecordingDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.logger.TrackingLoggerDriver;
import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;
import edu.kis.powp.jobs2d.drivers.transformations.TransformingDriver;

public interface DriverVisitor {
    void visit(CompositeDriver driver);
    void visit(LineDriverAdapter adapter);
    void visit(TrackingLoggerDriver driver);
    void visit(RealTimeDriver driver);
    void visit(RecordingDriver driver);
    void visit(TransformingDriver driver);
    void visit(BoundsDriver driver);
}
