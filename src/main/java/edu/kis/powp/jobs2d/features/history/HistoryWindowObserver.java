package edu.kis.powp.jobs2d.features.history;

import edu.kis.powp.observer.Subscriber;

public class HistoryWindowObserver implements Subscriber {

    private HistoryWindow historyWindow;

    public HistoryWindowObserver(HistoryWindow historyWindow) {
        this.historyWindow = historyWindow;
    }

    @Override
    public void update() {
        historyWindow.updateHistoryField();
    }

    public String toString() {
        return "History Window Observer";
    }
}
