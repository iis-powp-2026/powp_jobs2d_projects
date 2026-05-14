package edu.kis.powp.jobs2d.drivers;

import java.util.List;

import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Subscriber;

/**
 * Keeps the active driver wrapped in a {@link CompositeDriver} that forwards each
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

        CompositeDriver composite = new CompositeDriver("Recording");
        composite.addDriver(recordingSink);
        composite.addDriver(current);
        driverManager.setCurrentDriver(composite);
    }

    private boolean isRecordingComposite(VisitableDriver driver) {
        if (!(driver instanceof CompositeDriver)) {
            return false;
        }
        List<VisitableDriver> drivers = ((CompositeDriver) driver).getDrivers();
        return drivers.size() >= 2 && drivers.get(0) == recordingSink;
    }
}
