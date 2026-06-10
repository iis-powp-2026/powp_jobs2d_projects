package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.DriverCommand;

import java.time.Instant;

public class HistoryRecord {
    private DriverCommand command;
    private Instant datetime;

    public HistoryRecord(DriverCommand command, Instant datetime) {
        this.command = command;
        this.datetime = datetime;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public DriverCommand getCommand() {
        return command;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public void setCommand(DriverCommand command) {
        this.command = command;
    }
}
