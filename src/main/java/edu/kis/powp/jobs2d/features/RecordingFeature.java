package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.drivers.optionals.RecordingDriver;
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

        recordingDriver = new RecordingDriver();
    }

    public static RecordingDriver getRecordingDriver() {
        return recordingDriver;
    }
}