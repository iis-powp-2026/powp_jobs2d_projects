package edu.kis.powp.jobs2d.drivers.optionals;


import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Subscriber;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;

/**
 * Ensures that RecordingDriver remains the current driver in DriverManager.
 * Any time user selects a new driver, it becomes the target of RecordingDriver,
 * while RecordingDriver is set back as current.
 */
public class EnsureRecordingDriverIsCurrent implements Subscriber {
    private final DriverManager driverManager;
    private final RecordingDriver recordingDriver;
    private final CompositeDriver compositeDriver;
    private VisitableDriver currentTarget;

    public EnsureRecordingDriverIsCurrent(DriverManager driverManager, RecordingDriver recordingDriver, CompositeDriver compositeDriver) {
        this.driverManager = driverManager;
        this.recordingDriver = recordingDriver;
        this.compositeDriver = compositeDriver;
        this.currentTarget = driverManager.getCurrentDriver();
        if(currentTarget == compositeDriver){
            for(VisitableDriver driver : compositeDriver.getDrivers()){
                if(driver != recordingDriver){
                    currentTarget = driver;
                    break;
                }
            }
        }
    }

    @Override
    public void update() {
        VisitableDriver current = driverManager.getCurrentDriver();
        if (current == compositeDriver) {
            return;
        }
        compositeDriver.removeDriver(currentTarget);
        compositeDriver.addDriver(current);
        currentTarget = current;

        driverManager.setCurrentDriver(compositeDriver);
    }
}