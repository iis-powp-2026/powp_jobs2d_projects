package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.observer.Publisher;

import java.util.List;

/**
 * Driver command Manager.
 */
public class CommandManager {
    private DriverCommand currentCommand = null;
    private DriverCommand previousCommand = null;

    private Publisher changePublisher = new Publisher();

    /**
     * Set current command.
     * 
     * @param commandList Set the command as current.
     */
    public synchronized void setCurrentCommand(DriverCommand commandList) {
        previousCommand = currentCommand;
        this.currentCommand = commandList;
        changePublisher.notifyObservers();
    }

    public DriverCommand getPreviousCommand() {
        return previousCommand;
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
    public synchronized DriverCommand getCurrentCommand() {
        return currentCommand;
    }

    public synchronized void clearCurrentCommand() {
        currentCommand = null;
        changePublisher.notifyObservers();
    }

    public synchronized String getCurrentCommandString() {
        if (getCurrentCommand() == null) {
            return "No command loaded";
        } else
            return getCurrentCommand().toString();
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }
}
