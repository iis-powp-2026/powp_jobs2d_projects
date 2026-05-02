package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.features.history.HistoryManager;
import edu.kis.powp.jobs2d.features.history.SizeLimitHistorySubscriber;

public class HistoryFeature implements IFeature {

    private static HistoryManager historyManager;
    private static SizeLimitHistorySubscriber sizeLimitSubscriber;

    @Override
    public void setup(Application application) {
        historyManager = new HistoryManager();
        sizeLimitSubscriber = new SizeLimitHistorySubscriber(historyManager, 10);
        historyManager.getChangePublisher().addSubscriber(sizeLimitSubscriber);
    }

    @Override
    public String getName() {
        return "History";
    }

    public static HistoryManager getHistoryManager() {
        return historyManager;
    }

    public static SizeLimitHistorySubscriber getSizeLimitSubscriber() {
        return sizeLimitSubscriber;
    }
}
