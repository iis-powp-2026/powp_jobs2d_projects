package edu.kis.powp.jobs2d.command;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DriverCommandFactory {

    private static final Map<String, DriverCommand> commands = new LinkedHashMap<>();

    static {
        register("Top Secret", CompoundCommandFactory.createTopSecretCommand());
        register("Kite", CompoundCommandFactory.createKiteCommand());
        register("Rectangle", ImmutableCompoundCommandFactory.getRectangle(-100, 100, 200, 200));
        register("Shape Rectangle", ShapeCommandFactory.fromRectangle(200, 200, 0));
        register("Shape Circle", ShapeCommandFactory.fromCircle(0, 0, 100, 0));
    }

    public static void register(String name, DriverCommand command) {
        if (name == null || command == null) {
            return;
        }

        commands.put(name, command);
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

    public static Map<String, DriverCommand> getCommands() {
        Map<String, DriverCommand> copiedCommands = new LinkedHashMap<>();

        for (Map.Entry<String, DriverCommand> entry : commands.entrySet()) {
            copiedCommands.put(entry.getKey(), entry.getValue().deepCopy());
        }

        return copiedCommands;
    }
}