package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

public class RecordingCompositeDriver extends CompositeDriver {

    private final RecordingDriver recordingSink;

    public RecordingCompositeDriver(RecordingDriver recordingSink, VisitableDriver targetDriver) {
        super("Recording");
        this.recordingSink = recordingSink;
        addDriver(recordingSink);
        addDriver(targetDriver);
    }

    public boolean usesRecordingSink(RecordingDriver recordingSink) {
        return this.recordingSink == recordingSink;
    }
}
