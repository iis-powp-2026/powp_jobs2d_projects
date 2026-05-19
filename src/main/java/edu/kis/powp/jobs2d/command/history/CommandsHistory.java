package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.util.ArrayList;
import java.util.List;

public class CommandsHistory {
    private final List<HistoryRecord> history = new ArrayList<>();
    private Integer maxSize = 50;

    public CommandsHistory() {
        CommandsHistoryObserver observer = new CommandsHistoryObserver(history, maxSize);

        CommandsFeature
                .getDriverCommandManager()
                .getChangePublisher()
                .addSubscriber(observer);
    }

    public CommandsHistory(int maxSize) {
        this();
        this.maxSize = maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;

        while (history.size() >= maxSize) {
            history.remove(0);
        }
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public List<HistoryRecord> getHistory() {
        return history;
    }
}
