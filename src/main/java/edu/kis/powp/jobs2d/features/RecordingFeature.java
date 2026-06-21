package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.drivers.optionals.EnsureRecordingDriverIsCurrent;
import edu.kis.powp.jobs2d.drivers.optionals.RecordingDriver;
import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.appbase.Application;

public class RecordingFeature implements IFeature {

    private static RecordingDriver recordingDriver;

    @Override
    public String getName() {
        return "Recording feature";
    }

    @Override
    public void setup(Application application) {
        if (recordingDriver != null) {
            return;
        }

        DriverManager driverManager = DriverFeature.getDriverManager();
        recordingDriver = new RecordingDriver();
        CompositeDriver compositeDriver = new CompositeDriver("Recording Composite");
        compositeDriver.addDriver(recordingDriver);
        compositeDriver.addDriver(driverManager.getCurrentDriver());

        EnsureRecordingDriverIsCurrent subscriber = new EnsureRecordingDriverIsCurrent(driverManager, recordingDriver, compositeDriver);
        driverManager.getChangePublisher().addSubscriber(subscriber);

        subscriber.update();
    }

    public static RecordingDriver getRecordingDriver() {
        return recordingDriver;
    }
}