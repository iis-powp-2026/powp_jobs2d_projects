package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.features.RecordingFeature;

public class SelectClearRecordingOptionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        RecordingFeature.getRecordingDriver().clearRecorded();
        DriverFeature.updateDriverInfo();
    }
}