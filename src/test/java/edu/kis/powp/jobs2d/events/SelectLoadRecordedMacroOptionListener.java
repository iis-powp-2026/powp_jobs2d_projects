package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

public class SelectLoadRecordedMacroOptionListener implements ActionListener {

    private final DriverManager driverManager;

    public SelectLoadRecordedMacroOptionListener(DriverManager driverManager) {
        this.driverManager = driverManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<DriverCommand> recorded = new ArrayList<>(driverManager.getRecordingDriver().getRecordedCommands());
        CommandsFeature.getDriverCommandManager().setCurrentCommand(recorded, "RecordedMacro");
    }
}
