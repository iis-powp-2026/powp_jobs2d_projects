package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.drivers.RecordingDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class SelectToggleRecordingOptionListener implements ActionListener {

    private final RecordingDriver recordingDriver;

    public SelectToggleRecordingOptionListener(RecordingDriver recordingDriver) {
        this.recordingDriver = recordingDriver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        recordingDriver.setRecordingEnabled(!recordingDriver.isRecordingEnabled());
        DriverFeature.updateDriverInfo();
    }
}
