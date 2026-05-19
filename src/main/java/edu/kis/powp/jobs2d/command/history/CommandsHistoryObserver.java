package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.observer.Subscriber;

import java.time.Instant;
import java.util.List;


public class CommandsHistoryObserver implements Subscriber {
    private List<HistoryRecord> history;
    private Integer maxSize;

    public CommandsHistoryObserver(List<HistoryRecord> history, Integer maxSize) {
        this.history = history;
        this.maxSize = maxSize;
    }

    @Override
    public void update() {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        if (history.size() >= maxSize) {
            history.remove(0);
        }
        history.add(new HistoryRecord(command, Instant.now()));
    }
}
