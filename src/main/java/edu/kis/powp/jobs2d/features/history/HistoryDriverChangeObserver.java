package edu.kis.powp.jobs2d.features.history;

import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.observer.Subscriber;

public class HistoryDriverChangeObserver implements Subscriber {

    private HistoryManager historyManager;
    private DriverManager driverManager;

    public HistoryDriverChangeObserver(HistoryManager historyManager, DriverManager driverManager) {
        this.historyManager = historyManager;
        this.driverManager = driverManager;
    }

    @Override
    public void update() {
        if (driverManager.getCurrentDriver() != null) {
            historyManager.addHistory(new HistoryEntry("Driver set: " + driverManager.getCurrentDriver().toString(), null));
        }
    }

    public String toString() {
        return "History Driver Change Observer";
    }
}
