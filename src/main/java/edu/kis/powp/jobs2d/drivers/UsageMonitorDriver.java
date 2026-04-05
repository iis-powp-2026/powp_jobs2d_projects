package edu.kis.powp.jobs2d.drivers;
import edu.kis.powp.jobs2d.Job2dDriver;
import java.util.logging.Logger;

public class UsageMonitorDriver implements Job2dDriver {
    private final Job2dDriver baseDriver;
    private int currentX = 0;
    private int currentY = 0;
    private double headDistance = 0;
    private double operatingDistance = 0;

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public UsageMonitorDriver(Job2dDriver baseDriver) {
        this.baseDriver = baseDriver;
    }

    private double calculateDistance(int startX, int startY, int endX, int endY) {
        return Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
    }

    @Override
    public void setPosition(int x, int y) {
        double distance = calculateDistance(currentX, currentY, x, y);
        headDistance += distance;

        currentX = x;
        currentY = y;

        baseDriver.setPosition(x, y);

        printLog();
    }

    @Override
    public void operateTo(int x, int y) {
        double distance = calculateDistance(currentX, currentY, x, y);
        headDistance += distance;
        operatingDistance += distance;

        currentX = x;
        currentY = y;

        baseDriver.operateTo(x, y);

        printLog();
    }

    private void printLog() {
        logger.info(String.format("head distance: %.2f units, op. distance: %.2f units", headDistance, operatingDistance));
    }

    public double getHeadDistance() { return headDistance; }
    public double getOperatingDistance() { return operatingDistance; }
}