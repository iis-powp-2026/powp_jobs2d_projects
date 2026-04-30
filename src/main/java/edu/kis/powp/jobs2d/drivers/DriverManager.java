package edu.kis.powp.jobs2d.drivers;


import edu.kis.powp.jobs2d.drivers.logger.TrackingLoggerDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Publisher;

/**
 * Driver manager provides means to setup the driver. It also enables other
 * components and features of the application to react on configuration changes.
 */
public class DriverManager {

    private VisitableDriver currentDriver = new TrackingLoggerDriver();
    private Publisher changePublisher = new Publisher();

    /**
     * @param driver Set the driver as current.
     */
    public synchronized void setCurrentDriver(VisitableDriver driver) {
        currentDriver = driver;
        changePublisher.notifyObservers();
    }

    /**
     * @return Current driver.
     */
    public synchronized VisitableDriver getCurrentDriver() {
        return currentDriver;
    }

    /**
     * @return changePublisher.
     */
    public Publisher getChangePublisher() {
        return changePublisher;
    }
}
