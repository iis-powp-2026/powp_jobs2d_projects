package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.logger.TrackingLoggerDriver;
import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Publisher;

/**
 * Driver manager provides means to setup the driver. It also enables other
 * components and features of the application to react on configuration changes.
 */
public class DriverManager {

    private VisitableDriver coreDriver = new TrackingLoggerDriver();
    private CompositeDriver extensionsComposite = new CompositeDriver("Extensions");
    private Publisher changePublisher = new Publisher();

    public synchronized void setCurrentDriver(VisitableDriver driver) {
        coreDriver = driver;
        changePublisher.notifyObservers();
    }

    public synchronized void addExtension(VisitableDriver extension) {
        extensionsComposite.addDriver(extension);
        changePublisher.notifyObservers();
    }

    public synchronized void removeExtension(VisitableDriver extension) {
        extensionsComposite.removeDriver(extension);
        changePublisher.notifyObservers();
    }

    public synchronized VisitableDriver getCurrentDriver() {
        if (extensionsComposite.getDriverCount() == 0) {
            return coreDriver;
        }

        CompositeDriver activeDriver = new CompositeDriver(coreDriver.toString());
        activeDriver.addDriver(coreDriver);
        copyDrivers(extensionsComposite, activeDriver);
        return activeDriver;
    }

    private void copyDrivers(CompositeDriver source, CompositeDriver target) {
        for (VisitableDriver driver : source.getDrivers()) {
            target.addDriver(driver);
        }
    }

    public synchronized VisitableDriver getCoreDriver() {
        return coreDriver;
    }

    /**
     * @return changePublisher.
     */
    public Publisher getChangePublisher() {
        return changePublisher;
    }
}
