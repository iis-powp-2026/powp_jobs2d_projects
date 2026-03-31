package edu.kis.powp.jobs2d.command.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;

/**
 * Abstract composite command implementing composite pattern.
 */
public abstract class AbstractCommandComposite implements ICompoundCommand {

    protected List<DriverCommand> commands = new ArrayList<>();

    /**
     * Add command to composite.
     *
     * @param command command to add
     */
    public void add(DriverCommand command) {
        commands.add(command);
    }

    /**
     * Remove command from composite by index.
     *
     * @param index position of command to remove
     * @return removed command
     */
    public DriverCommand remove(int index) {
        return commands.remove(index);
    }

    /**
     * Get command at index.
     *
     * @param index position
     * @return command at given index
     */
    public DriverCommand get(int index) {
        return commands.get(index);
    }

    /**
     * Get number of commands in composite.
     *
     * @return size of commands list
     */
    public int size() {
        return commands.size();
    }

    /**
     * Clear all commands from composite.
     */
    public void clear() {
        commands.clear();
    }

    @Override
    public void execute(Job2dDriver driver) {
        for (DriverCommand command : commands) {
            command.execute(driver);
        }
    }

    @Override
    public Iterator<DriverCommand> iterator() {
        return commands.iterator();
    }

}

