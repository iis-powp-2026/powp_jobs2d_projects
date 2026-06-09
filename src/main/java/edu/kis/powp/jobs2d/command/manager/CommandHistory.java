package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores history of commands that have been set as current.
 */
public class CommandHistory implements ICommandHistory {
    private final List<DriverCommand> history = new ArrayList<>();

    public void addCommand(DriverCommand command) {
        if (command != null) {
            history.add(command);
        }
    }

    public List<DriverCommand> getHistory() {
        return Collections.unmodifiableList(history);
    }
}