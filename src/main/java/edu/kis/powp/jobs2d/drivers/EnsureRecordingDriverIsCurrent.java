package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Subscriber;

/**
 * Keeps the active driver wrapped in a {@link RecordingCompositeDriver} that forwards each
 * call to the shared {@link RecordingDriver} (capture-only) and to the user's
 * selected device driver.
 */
public class EnsureRecordingDriverIsCurrent implements Subscriber {

    private final DriverManager driverManager;
    private final RecordingDriver recordingSink;

    public EnsureRecordingDriverIsCurrent(DriverManager driverManager, RecordingDriver recordingSink) {
        this.driverManager = driverManager;
        this.recordingSink = recordingSink;
    }

    @Override
    public void update() {
        VisitableDriver current = driverManager.getCurrentDriver();

        if (current == recordingSink) {
            return;
        }

        if (isRecordingComposite(current)) {
            return;
        }

        driverManager.setCurrentDriver(new RecordingCompositeDriver(recordingSink, current));
    }

    private boolean isRecordingComposite(VisitableDriver driver) {
        return driver instanceof RecordingCompositeDriver
                && ((RecordingCompositeDriver) driver).usesRecordingSink(recordingSink);
    }
}
