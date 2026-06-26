package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.DriverCommand;
import java.util.ArrayList;
import java.util.List;

public class BuilderMemento {
    private final List<DriverCommand> commands;
    private final String name;

    public BuilderMemento(List<DriverCommand> commands, String name) {
        this.commands = new ArrayList<>(commands);
        this.name = name;
    }

    public List<DriverCommand> getCommands() {
        return new ArrayList<>(this.commands);
    }

    public String getName() {
        return this.name;
    }
}