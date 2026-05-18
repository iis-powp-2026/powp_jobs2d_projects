package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.HistoryFeature;
import edu.kis.powp.jobs2d.features.history.HistoryEntry;

public class SelectRunCurrentCommandOptionListener implements ActionListener {

    private DriverManager driverManager;

    public SelectRunCurrentCommandOptionListener(DriverManager driverManager) {
        this.driverManager = driverManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        command.execute(driverManager.getCurrentDriver());
        HistoryFeature.getHistoryManager().addHistory(
                new HistoryEntry("Command executed: " + command.toString(), command));
    }
}