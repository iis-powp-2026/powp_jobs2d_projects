package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.features.DeviceUsageFeature;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Responsible for creating DeviceUsageManager instances and decorating drivers with DeviceUsageDriverDecorator.
 */
public class DeviceUsageRegistrar {

    private static final Map<String, DeviceUsageManager> managers = new ConcurrentHashMap<>();

    /**
     * Create a DeviceUsageManager, decorate the provided driver and register manager in UI under given name.
     *
     * @param driver driver to decorate
     * @param name   name under which manager will be registered in UI
     * @return decorated driver
     */
    public static VisitableDriver decorateAndRegister(VisitableDriver driver, String name) {
        DeviceUsageManager mgr = new DeviceUsageManager();
        VisitableDriver decorated = new DeviceUsageDriverDecorator(driver, mgr);

        String key = (name != null) ? name : decorated.toString();
        managers.put(key, mgr);

        try {
            DeviceUsageFeature.registerManager(mgr, key);
        } catch (Throwable t) {
            // ignored
        }

        return decorated;
    }

    /**
     * Convenience method using driver's toString() as name.
     *
     * @param driver driver to decorate
     * @return decorated driver
     */
    public static VisitableDriver decorateAndRegister(VisitableDriver driver) {
        return decorateAndRegister(driver, driver != null ? driver.toString() : "unknown-driver");
    }

    public static DeviceUsageManager getManagerByName(String name) {
        return managers.get(name);
    }
}