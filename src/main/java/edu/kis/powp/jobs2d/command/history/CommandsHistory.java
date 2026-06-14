package edu.kis.powp.jobs2d.command.history;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class CommandsHistory {
    private final DefaultListModel<HistoryRecord> historyModel = new DefaultListModel<>();
    private Integer maxSize = 50;

    public CommandsHistory(int maxSize) {
        this.maxSize = maxSize;
    }

    public CommandsHistory() {
        this(50);
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        while (historyModel.size() >= maxSize) {
            historyModel.remove(0);
        }
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public DefaultListModel<HistoryRecord> getHistoryModel() {
        return historyModel;
    }

    public List<HistoryRecord> getHistory() {
        return Collections.list(historyModel.elements());
    }
}
