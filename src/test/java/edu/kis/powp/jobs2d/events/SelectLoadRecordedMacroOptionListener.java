package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.RecordingFeature;

public class SelectLoadRecordedMacroOptionListener implements ActionListener {
    
    public SelectLoadRecordedMacroOptionListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<DriverCommand> recorded = new ArrayList<>(RecordingFeature.getRecordingDriver().getRecordedCommands());
        CommandsFeature.getDriverCommandManager().setCurrentCommand(recorded, "RecordedMacro");
    }
}
