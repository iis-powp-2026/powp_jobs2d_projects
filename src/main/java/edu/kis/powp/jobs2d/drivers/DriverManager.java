package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;
import edu.kis.powp.observer.Publisher;

import java.util.ArrayList;

/**
 * Driver manager provides means to setup the driver. It also enables other
 * components and features of the application to react on configuration changes.
 */
public class DriverManager {

    private Job2dDriver baseDriver;
    private ArrayList<Job2dDriver> extensions = new ArrayList<>();
    private Publisher changePublisher = new Publisher();

    /**
     * @param driver Set the driver as current.
     */
    public synchronized void setBaseDriver(Job2dDriver driver) {
        baseDriver = driver;
        changePublisher.notifyObservers();
    }


    /**
     * @param extension Add the driver to the extensions.
     */
    public synchronized void addExtension(Job2dDriver extension) {
        extensions.add(extension);
        changePublisher.notifyObservers();
    }

    /**
     * @param extension Remove the driver from the extensions.
     */
    public synchronized void removeExtension(Job2dDriver extension) {
        extensions.remove(extension);
        changePublisher.notifyObservers();
    }


    /**
     * @return Current driver as CompositeDriver of base driver with extensions.
     */
    public synchronized Job2dDriver getCurrentDriver() {
        ArrayList<Job2dDriver> allDrivers = new ArrayList<>();

        if (baseDriver != null) {
            allDrivers.add(baseDriver);
        }

        allDrivers.addAll(extensions);

        String name = buildCompositeName(allDrivers);
        return new CompositeDriver(name,allDrivers);
    }


    /**
     * @return Base driver.
     */
    public synchronized Job2dDriver getBaseDriver() {
        return baseDriver;
    }

    /**
     * @return changePublisher.
     */
    public Publisher getChangePublisher() {
        return changePublisher;
    }

    /**
     * Helper function to set the correct name for current driver
     */
    private String buildCompositeName(ArrayList<Job2dDriver> drivers) {
        if (drivers.isEmpty()) {
            return "Empty Composite";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < drivers.size(); i++) {
            sb.append(drivers.get(i).toString());

            if (i < drivers.size() - 1) {
                sb.append(" + ");
            }
        }

        return sb.toString();
    }
}