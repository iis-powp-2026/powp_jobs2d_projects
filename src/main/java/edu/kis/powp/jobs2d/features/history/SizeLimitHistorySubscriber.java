package edu.kis.powp.jobs2d.features.history;

import edu.kis.powp.observer.Subscriber;

public class SizeLimitHistorySubscriber implements Subscriber {

    private final HistoryManager historyManager;
    private int maxSize;

    public SizeLimitHistorySubscriber(HistoryManager historyManager, int maxSize) {
        this.historyManager = historyManager;
        this.maxSize = maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        update();
    }

    public int getMaxSize() {
        return maxSize;
    }

    private boolean isCleaning = false;

    @Override
    public void update() {
        if (isCleaning) return;
        isCleaning = true;
        
        while (historyManager.getHistoryList().size() > maxSize) {
            historyManager.removeFirst();
        }
        
        isCleaning = false;
    }

    @Override
    public String toString() {
        return "Size Limit History Subscriber (" + maxSize + ")";
    }
}
