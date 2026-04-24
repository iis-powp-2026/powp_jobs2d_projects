package edu.kis.powp.jobs2d.features.history;

import edu.kis.powp.observer.Publisher;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private List<String> historyList = new ArrayList<>();
    private Publisher changePublisher = new Publisher();

    public void addHistory(String history) {
        historyList.add(history);
        changePublisher.notifyObservers();
    }

    public List<String> getHistoryList() {
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
