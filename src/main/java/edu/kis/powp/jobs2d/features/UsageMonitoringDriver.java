package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

import java.util.ArrayList;
import java.util.List;

public class UsageMonitoringDriver implements VisitableDriver {

    private final Job2dDriver driver;

    private int lastX;
    private int lastY;
    private boolean initialized = false;

    private double totalDistance = 0;
    private double operationDistance = 0;


    private final List<UsageListener> listeners = new ArrayList<>();

    public UsageMonitoringDriver(Job2dDriver driver) {
        this.driver = driver;
    }


    public void addListener(UsageListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (UsageListener l : listeners) {
            l.usageChanged(totalDistance, operationDistance);
        }
    }


    @Override
    public void setPosition(int x, int y) {

        if (!initialized) {
            lastX = x;
            lastY = y;
            initialized = true;
            driver.setPosition(x, y);
            return;
        }

        totalDistance += distance(lastX, lastY, x, y);


        lastX = x;
        lastY = y;

        driver.setPosition(x, y);
        notifyListeners();
    }

    @Override
    public void operateTo(int x, int y) {
        operationDistance = 0;
        if (!initialized) {
            lastX = x;
            lastY = y;
            initialized = true;
            driver.operateTo(x, y);
            return;
        }
        System.out.println("MOVE: " + lastX + "," + lastY + " -> " + x + "," + y);

        double d = distance(lastX, lastY, x, y);

        totalDistance += d;
        operationDistance += d;


        lastX = x;
        lastY = y;

        driver.operateTo(x, y);
        notifyListeners();
    }

    private double distance(int x1, int y1, int x2, int y2) {
        return Math.hypot(x2 - x1, y2 - y1);
    }

    @Override
    public void accept(DriverVisitor visitor) {
        ((VisitableDriver) driver).accept(visitor);
    }
}