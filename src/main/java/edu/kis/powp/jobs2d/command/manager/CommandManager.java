package edu.kis.powp.jobs2d.command.manager;

import java.util.Iterator;
import java.util.List;


import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.gui.ICommandManager;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.observer.Publisher;

/**
 * Driver command Manager.
 */
public class CommandManager implements ICommandManager, ICommandRunner {
    private DriverCommand currentCommand = null;

    private Publisher changePublisher = new Publisher();

    private DriverManager driverManager;

    /**
     * Set current command.
     * 
     * @param commandList Set the command as current.
     */
    @Override
    public synchronized void setCurrentCommand(DriverCommand commandList) {
        this.currentCommand = commandList;
        changePublisher.notifyObservers();
    }

    /**
     * Set current command.
     * 
     * @param commandList list of commands representing a compound command.
     * @param name        name of the command.
     */
    public synchronized void setCurrentCommand(List<DriverCommand> commandList, String name) {
        setCurrentCommand(new ImmutableCompoundCommand(name, commandList));
    }

    /**
     * Return current command.
     * 
     * @return Current command.
     */
    @Override
    public synchronized DriverCommand getCurrentCommand() {
        return currentCommand;
    }

    @Override
    public synchronized void clearCurrentCommand() {
        currentCommand = null;
    }

    @Override
    public synchronized String getCurrentCommandString() {
        if (getCurrentCommand() == null) {
            return "No command loaded";
        } else
            return getCurrentCommand().toString();
    }

    @Override
    public Publisher getChangePublisher() {
        return changePublisher;
    }

    public void setDriverManager(DriverManager driverManager) {
        this.driverManager = driverManager;
    }

    @Override
    public void run(DriverCommand command) {
        if (command != null && driverManager != null) {
            command.execute(driverManager.getCurrentDriver());
        }
    }
}
