package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.EnsureRecordingDriverIsCurrent;
import edu.kis.powp.jobs2d.drivers.RecordingDriver;

public class RecordingFeature implements IFeature {

    private static RecordingDriver recordingDriver;

    @Override
    public void setup(Application application) {
        if (recordingDriver != null) {
            return;
        }

        recordingDriver = new RecordingDriver();

        EnsureRecordingDriverIsCurrent subscriber = new EnsureRecordingDriverIsCurrent(
                DriverFeature.getDriverManager(), recordingDriver);
        DriverFeature.getDriverManager().getChangePublisher().addSubscriber(subscriber);

        subscriber.update();
    }

    @Override
    public String getName() {
        return "Recording";
    }

    public static RecordingDriver getRecordingDriver() {
        return recordingDriver;
    }
}
