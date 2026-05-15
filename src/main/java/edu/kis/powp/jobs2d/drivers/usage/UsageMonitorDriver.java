package edu.kis.powp.jobs2d.drivers.usage;


import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Publisher;


public class UsageMonitorDriver implements VisitableDriver, IUsageMonitor {
    private final VisitableDriver innerDriver;

    private final Publisher publisher = new Publisher();

    private int currentX = 0;
    private int currentY = 0;
    private double headDistance = 0;
    private double operatingDistance = 0;

    public UsageMonitorDriver() {
        this.innerDriver = null;
    }

    public UsageMonitorDriver(VisitableDriver innerDriver) {
        this.innerDriver = innerDriver;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    private double calculateDistance(int targetX, int targetY) {
        return Math.sqrt(Math.pow(targetX - currentX, 2) + Math.pow(targetY - currentY, 2));
    }

    @Override
    public void setPosition(int x, int y) {
        headDistance += calculateDistance(x, y);
        currentX = x;
        currentY = y;
        if (innerDriver != null) {
            innerDriver.setPosition(x, y);
        }


        publisher.notifyObservers();
    }

    @Override
    public void operateTo(int x, int y) {
        double distance = calculateDistance(x, y);
        headDistance += distance;
        operatingDistance += distance;
        currentX = x;
        currentY = y;
        if (innerDriver != null) {
            innerDriver.operateTo(x, y);
        }


        publisher.notifyObservers();
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
        return innerDriver != null
                ? "Monitored " + innerDriver
                : "Usage Monitor (extension)";
    }


    public VisitableDriver getInnerDriver() {
        return innerDriver;
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}