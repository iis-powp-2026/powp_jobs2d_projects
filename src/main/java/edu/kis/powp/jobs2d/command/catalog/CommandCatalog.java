package edu.kis.powp.jobs2d.command.catalog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.observer.Publisher;

public class CommandCatalog {

    private Map<String, DriverCommand> commands = new LinkedHashMap<>();

    private Publisher changePublisher = new Publisher();

    public void addCommand(String name, DriverCommand command) {
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        if (command == null) {
            return;
        }

        commands.put(name.trim(), command.deepCopy());
        changePublisher.notifyObservers();
    }

    public DriverCommand getCommand(String name) {
        DriverCommand command = commands.get(name);

        if (command == null) {
            return null;
        }

        return command.deepCopy();
    }

    public List<String> getCommandNames() {
        return new ArrayList<>(commands.keySet());
    }

    public boolean containsCommand(String name) {
        return commands.containsKey(name);
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }
}