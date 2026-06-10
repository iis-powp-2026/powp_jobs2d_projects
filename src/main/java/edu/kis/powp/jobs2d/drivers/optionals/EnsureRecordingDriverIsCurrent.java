package edu.kis.powp.jobs2d.drivers.optionals;

import edu.kis.powp.jobs2d.drivers.DriverChainUtils;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Subscriber;

/**
 * Ensures that RecordingDriver is always present in the driver chain.
 * Instead of simple identity check, traverses the entire decorator chain
 * to find RecordingDriver — this prevents stack overflow when extensions
 * (e.g. LoggingExtensionDriver) are stacked on top of RecordingDriver.
 * When a new driver is selected, RecordingDriver wraps it and is placed
 * at the top of the chain.
 */
public class EnsureRecordingDriverIsCurrent implements Subscriber {
    private final DriverManager driverManager;
    private final RecordingDriver recordingDriver;

    public EnsureRecordingDriverIsCurrent(DriverManager driverManager, RecordingDriver recordingDriver) {
        this.driverManager = driverManager;
        this.recordingDriver = recordingDriver;
    }

    @Override
    public void update() {
        VisitableDriver current = driverManager.getCurrentDriver();

        // Traverse the full decorator chain — if recording is anywhere in it, do nothing.
        if (DriverChainUtils.isInChain(current, recordingDriver)) {
            return;
        }

        // Recording is not in the chain — wrap current driver and set as outermost.
        recordingDriver.setTarget(current);
        driverManager.setCurrentDriver(recordingDriver);
    }
}
