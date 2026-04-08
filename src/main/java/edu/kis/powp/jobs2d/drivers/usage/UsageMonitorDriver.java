package edu.kis.powp.jobs2d.drivers.usage;

import edu.kis.powp.jobs2d.Job2dDriver;
import java.util.ArrayList;
import java.util.List;

public class UsageMonitorDriver implements Job2dDriver, IUsageMonitor {
    private final Job2dDriver innerDriver;

    private final List<UsageSubscriber> subscribers = new ArrayList<>();

    private int currentX = 0;
    private int currentY = 0;
    private double headDistance = 0;
    private double operatingDistance = 0;

    public UsageMonitorDriver(Job2dDriver innerDriver) {
        this.innerDriver = innerDriver;
    }

    public void addSubscriber(UsageSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    private void notifySubscribers() {
        for (UsageSubscriber subscriber : subscribers) {
            subscriber.update(headDistance, operatingDistance);
        }
    }

    private double calculateDistance(int targetX, int targetY) {
        return Math.sqrt(Math.pow(targetX - currentX, 2) + Math.pow(targetY - currentY, 2));
    }

    @Override
    public void setPosition(int x, int y) {
        headDistance += calculateDistance(x, y);
        currentX = x;
        currentY = y;
        innerDriver.setPosition(x, y);

        notifySubscribers();
    }

    @Override
    public void operateTo(int x, int y) {
        double distance = calculateDistance(x, y);
        headDistance += distance;
        operatingDistance += distance;
        currentX = x;
        currentY = y;
        innerDriver.operateTo(x, y);

        notifySubscribers();
    }

    @Override
    public double getHeadDistance() {
        return headDistance;
    }

    @Override
    public double getOperatingDistance() {
        return operatingDistance;
    }

    @Override
    public String toString() {
        return "Monitored " + innerDriver.toString();
    }
}