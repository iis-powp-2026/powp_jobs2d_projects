package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.observer.Subscriber;

/**
 * Observer responsible for updating command history.
 */
public class CommandHistoryObserver implements Subscriber {

    private final CommandHistory history;

    public CommandHistoryObserver(CommandHistory history) {
        this.history = history;
    }

    @Override
    public void update() {
        DriverCommand command =
                CommandsFeature.getDriverCommandManager().getCurrentCommand();

        history.addCommand(command);
    }

    @Override
    public String toString() {
        return "Command History Observer";
    }
}