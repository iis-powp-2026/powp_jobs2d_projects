package edu.kis.powp.jobs2d.command;



import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImmutableCompoundCommand implements ICompoundCommand {
    private List<DriverCommand> commands;
    private String name;

    /**
     * Create immutable compound command with sequence of commands.
     * 
     * @param name     name of the compound command, optional
     * @param commands list of the commands
     */
    public ImmutableCompoundCommand(String name, List<DriverCommand> commands) {
        if (name == null) {
            this.name = "ImmutableCompoundCommand";
        } else {
            this.name = name;
        }
        this.commands = new ArrayList<>();
        for (DriverCommand cmd : commands) {
            this.commands.add(cmd.deepCopy());
        }
    }

    /**
     * Get the name of this compound command.
     *
     * @return name of the command, or null if not set
     */
    public String getName() {
        return name;
    }

    /**
     * Get the number of commands in this compound command.
     *
     * @return number of commands
     */
    public int getCommandCount() {
        return commands.size();
    }

    /**
     * Get iterator over commands.
     *
     * @return iterator over commands
     */
    @Override
    public Iterator<DriverCommand> iterator() {
        List<DriverCommand> copy = new ArrayList<>();
        for (DriverCommand cmd : commands) {
            copy.add(cmd.deepCopy());
        }
        return copy.iterator();
    }

    /**
     * Execute all commands in sequence on the given driver.
     *
     * @param driver the driver to execute commands on
     */
    @Override
    public void execute(VisitableDriver driver) {
        for (DriverCommand command : commands) {
            command.execute(driver);
        }
    }

    @Override
    public String toString() {
        if (name != null) {
            return name;
        }
        return "ImmutableCompoundCommand";
    }

    @Override
    public ImmutableCompoundCommand deepCopy() {
        List<DriverCommand> copiedCommands = new ArrayList<>();
        for (DriverCommand cmd : commands) {
            copiedCommands.add(cmd.deepCopy());
        }
        return new ImmutableCompoundCommand(this.name, copiedCommands);
    }
}
