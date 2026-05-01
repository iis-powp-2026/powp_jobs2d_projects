package edu.kis.powp.jobs2d.command;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import edu.kis.powp.observer.Publisher;

public class DriverCommandFactory {

    private static final Map<String, DriverCommand> commands = new LinkedHashMap<>();

    private static final Publisher commandCatalogChangePublisher = new Publisher();

    static {
        registerCommand("TopSecretCommand", CompoundCommandFactory.createTopSecretCommand());
        registerCommand("KiteCommand", CompoundCommandFactory.createKiteCommand());
        registerCommand("Immutable Rectangle", ImmutableCompoundCommandFactory.getRectangle(0, 0, 100, 150));
    }

    private DriverCommandFactory() {}

    public static void registerCommand(String name, DriverCommand command) {
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        if (command == null) {
            return;
        }

        commands.put(name, command.deepCopy());
        commandCatalogChangePublisher.notifyObservers();
    }

    public static DriverCommand getCommand(String name) {
        DriverCommand command = commands.get(name);

        if (command == null) {
            return null;
        }

        return command.deepCopy();
    }

    public static Set<String> getCommandNames() {
        return commands.keySet();
    }

    public static boolean containsCommand(String name) {
        return commands.containsKey(name);
    }

    public static Publisher getCommandCatalogChangePublisher() {
        return commandCatalogChangePublisher;
    }
}