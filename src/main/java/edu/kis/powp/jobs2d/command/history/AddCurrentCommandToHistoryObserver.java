package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.observer.Subscriber;

import javax.swing.*;
import java.time.Instant;


public class AddCurrentCommandToHistoryObserver implements Subscriber { private Integer maxSize;
    private final DefaultListModel<HistoryRecord> historyModel;

    public AddCurrentCommandToHistoryObserver(DefaultListModel<HistoryRecord> historyModel, Integer maxSize) {
        this.historyModel = historyModel;
        this.maxSize = maxSize;
    }

    @Override
    public void update() {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        if (historyModel.size() >= maxSize) {
            historyModel.remove(0);
        }
        historyModel.addElement(new HistoryRecord(command, Instant.now()));
    }
}
