package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.DriverCommandFactory;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

public class SelectLoadSecretCommandOptionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        DriverCommand topSecret = DriverCommandFactory.getCommand("TopSecretCommand");

        CommandManager manager = CommandsFeature.getDriverCommandManager();
        manager.setCurrentCommand(topSecret);
    }
}