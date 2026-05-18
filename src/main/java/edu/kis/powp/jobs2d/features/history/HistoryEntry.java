package edu.kis.powp.jobs2d.features.history;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.kis.powp.jobs2d.command.DriverCommand;

public class HistoryEntry {
    private final DriverCommand command;
    private final LocalDateTime time;
    private final String name;

    public HistoryEntry(String name, DriverCommand command) {
        this.name = name;
        this.command = command != null ? command.deepCopy() : null;
        this.time = LocalDateTime.now();
    }

    public DriverCommand getCommand() {
        return command;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + time.format(formatter) + "] " + name;
    }
}
