package edu.kis.powp.jobs2d.command;
import java.util.ArrayList;
import java.util.List;

public class SimpleComplexCommandBuilder {

    private List<DriverCommand> commands;
    private String name;

    public SimpleComplexCommandBuilder() {
        this.commands = new ArrayList<>();
        this.name = null;
    }
    
    public SimpleComplexCommandBuilder(String name) {
        this.commands = new ArrayList<>();
        this.name = name;
    }

    public SimpleComplexCommandBuilder addCommand(DriverCommand command) {
        if (command != null)
            this.commands.add(command);
        return this;
    }

    public SimpleComplexCommandBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SimpleComplexCommandBuilder setPosition(int x, int y) {
        this.commands.add(new SetPositionCommand(x, y));
        return this;
    }

    public SimpleComplexCommandBuilder operateTo(int x, int y) {
        this.commands.add(new OperateToCommand(x, y));
        return this;
    }

    public CompoundCommand build() {
        return new CompoundCommand(this.commands, this.name);
    }

    public void reset() {
        this.commands.clear();
        this.name = null;
    }
}
