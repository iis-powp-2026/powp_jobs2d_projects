package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.observer.Subscriber;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// TODO:
//  history limit size - DP,
//  [x] command execution time,
//  clearing history -DP,
//  tests -DR, 
//  some getter to get history -DR

public class CommandsHistoryObserver implements Subscriber {
    public static class HistoryRecord {
        private DriverCommand command;

        public HistoryRecord(DriverCommand command, Instant datetime) {
            this.command = command;
            this.datetime = datetime;
        }

        public Instant getDatetime() {
            return datetime;
        }

        public void setDatetime(Instant datetime) {
            this.datetime = datetime;
        }

        public DriverCommand getCommand() {
            return command;
        }

        public void setCommand(DriverCommand command) {
            this.command = command;
        }

        private Instant datetime;

    }

    private List<HistoryRecord> history = new ArrayList<>();

    @Override
    public void update() {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        history.add(new HistoryRecord(command, Instant.now()));
    }

}
