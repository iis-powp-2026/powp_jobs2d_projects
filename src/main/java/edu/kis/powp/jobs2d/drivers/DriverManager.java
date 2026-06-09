package edu.kis.powp.jobs2d.drivers;


import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Publisher;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Driver manager provides means to setup the driver. It also enables other
 * components and features of the application to react on configuration changes.
 */
public class DriverManager {

    private VisitableDriver baseDriver;
    private VisitableDriver decoratedBaseDriver; // Cached decorated version
    private final Map<String, VisitableDriver> extensions = new LinkedHashMap<>();
    private Publisher changePublisher = new Publisher();

    /**
     * @param driver Set the driver as current.
     */
    public synchronized void setCurrentDriver(VisitableDriver driver) {
        this.baseDriver = driver;
        this.decoratedBaseDriver = null; // Clear cache when driver changes
        changePublisher.notifyObservers();
    }

    /**
     * Adds the given extension to extensions list.
     */
    public synchronized void addExtension(String name, VisitableDriver extension) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Extension name must not be blank.");
        }
        if (extension == null) {
            throw new IllegalArgumentException("Extension driver must not be null.");
        }
        extensions.put(name, extension);

        // Clear cached decorated driver when extension changes
        if ("usage-monitor".equals(name)) {
            decoratedBaseDriver = null;
        }

        changePublisher.notifyObservers();
    }


    /**
     * Removes the extension registered under {@code name}. Does nothing if no
     * extension with that name exists.
     */
    public synchronized void removeExtension(String name) {
        if (extensions.remove(name) != null) {
            // Clear cached decorated driver when extension changes
            if ("usage-monitor".equals(name)) {
                decoratedBaseDriver = null;
            }
            changePublisher.notifyObservers();
        }
    }


    public synchronized boolean hasExtension(String name) {
        return extensions.containsKey(name);
    }


    /**
     * @return Current driver as composite of base driver and all extension drivers.
     * If "usage-monitor" extension is active, wraps the base driver with DeviceUsageDriverDecorator.
     */
    public synchronized VisitableDriver getCurrentDriver() {
        VisitableDriver activeDriver = baseDriver;

        // Wrap base driver with DeviceUsageDriverDecorator if usage-monitor extension is active
        if (baseDriver != null && hasExtension("usage-monitor")) {
            // Use cached decorated driver if available
            if (decoratedBaseDriver == null) {
                // Get or create DeviceUsageManager for this driver
                DeviceUsageManager manager = DeviceUsageRegistrar.getManagerForDriver(baseDriver);
                if (manager != null) {
                    decoratedBaseDriver = new DeviceUsageDriverDecorator(baseDriver, manager);
                } else {
                    // Fallback: should not happen if driver was registered properly
                    activeDriver = baseDriver;
                }
            }
            activeDriver = decoratedBaseDriver;
        } else {
            // usage-monitor not active, use base driver directly
            activeDriver = baseDriver;
            decoratedBaseDriver = null;
        }

        if (activeDriver == null && extensions.isEmpty()) {
            return new CompositeDriver("Empty driver");
        }

        CompositeDriver composite = new CompositeDriver("Active Driver + Extensions");

        if (activeDriver != null) {
            composite.getDrivers().add(activeDriver);
        }

        composite.getDrivers().addAll(extensions.values());

        return composite;
    }


    /**
     * @return changePublisher.
     */
    public Publisher getChangePublisher() {
        return changePublisher;
    }
}
