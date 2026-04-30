package edu.kis.powp.jobs2d.drivers;


import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Subscriber;

/**
 * Ensures that RecordingDriver remains the current driver in DriverManager.
 * Any time user selects a new driver, it becomes the target of RecordingDriver,
 * while RecordingDriver is set back as current.
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

        if (current == recordingDriver) {
            return;
        }

        recordingDriver.setTarget(current);
        driverManager.setCurrentDriver(recordingDriver);
    }
}