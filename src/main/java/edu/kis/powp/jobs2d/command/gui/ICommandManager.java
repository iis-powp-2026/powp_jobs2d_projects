package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.observer.Publisher;

public interface ICommandManager {
    DriverCommand getCurrentCommand();
    void setCurrentCommand(DriverCommand command);
    String getCurrentCommandString();
    void clearCurrentCommand();
    Publisher getChangePublisher();
}