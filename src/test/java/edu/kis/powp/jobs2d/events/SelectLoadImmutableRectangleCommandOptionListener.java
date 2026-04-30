package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.DriverCommandFactory;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectLoadImmutableRectangleCommandOptionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        DriverCommand command = DriverCommandFactory.getCommand("Immutable Rectangle");

        CommandManager manager = CommandsFeature.getDriverCommandManager();
        manager.setCurrentCommand(command);
    }
}