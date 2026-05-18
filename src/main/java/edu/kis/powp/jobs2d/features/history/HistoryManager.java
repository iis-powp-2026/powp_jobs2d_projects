package edu.kis.powp.jobs2d.features.history;

import edu.kis.powp.observer.Publisher;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private List<HistoryEntry> historyList = new ArrayList<>();
    private Publisher changePublisher = new Publisher();

    public void addHistory(HistoryEntry history) {
        historyList.add(history);
        changePublisher.notifyObservers();
    }

    public void removeFirst() {
        if (!historyList.isEmpty()) {
            historyList.remove(0);
            changePublisher.notifyObservers();
        }
    }

    public List<HistoryEntry> getHistoryList() {
        return historyList;
    }

    public void clearHistory() {
        historyList.clear();
        changePublisher.notifyObservers();
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }
}
