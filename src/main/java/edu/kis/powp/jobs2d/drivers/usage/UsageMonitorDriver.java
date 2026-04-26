package edu.kis.powp.jobs2d.drivers.usage;

import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Publisher;

public class UsageMonitorDriver implements VisitableDriver {
    private VisitableDriver driver;
    private double headDistance = 0;
    private double operatingDistance = 0;
    private int startX = 0;
    private int startY = 0;
    private Publisher changePublisher = new Publisher();

    public UsageMonitorDriver(VisitableDriver driver) {
        this.driver = driver;
    }

    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public void setPosition(int x, int y) {
        double distance = calculateDistance(startX, startY, x, y);
        headDistance += distance;
        startX = x;
        startY = y;
        driver.setPosition(x, y);
        changePublisher.notifyObservers();
    }

    @Override
    public void operateTo(int x, int y) {
        double distance = calculateDistance(startX, startY, x, y);
        headDistance += distance;
        operatingDistance += distance;
        startX = x;
        startY = y;
        driver.operateTo(x, y);
        changePublisher.notifyObservers();
    }

    public double getHeadDistance() {
        return headDistance;
    }

    public double getOperatingDistance() {
        return operatingDistance;
    }

    public VisitableDriver getInnerDriver() {
        return driver;
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        return "Usage Monitor for: " + driver.toString();
    }
}
