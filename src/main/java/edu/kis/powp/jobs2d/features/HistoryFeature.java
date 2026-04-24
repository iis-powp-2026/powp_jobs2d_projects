package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.features.history.HistoryManager;

public class HistoryFeature implements IFeature {

    private static HistoryManager historyManager;

    @Override
    public void setup(Application application) {
        historyManager = new HistoryManager();
    }

    @Override
    public String getName() {
        return "History";
    }

    public static HistoryManager getHistoryManager() {
        return historyManager;
    }
}
