package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.gui.DeviceManagementWindow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Responsible for creating DeviceUsageManager instances for drivers.
 * Tracks whether usage monitoring is currently enabled (via usage-monitor extension).
 */
public class DeviceUsageRegistrar {

    private static final Map<String, DeviceUsageManager> managers = new ConcurrentHashMap<>();
    private static final Map<VisitableDriver, DeviceUsageManager> driverToManagerMap = new ConcurrentHashMap<>();
    private static DeviceManagementWindow deviceManagementWindow;
    private static volatile boolean usageMonitoringEnabled = false;

    /**
     * Set the Device Management Window that will receive manager registrations.
     */
    public static void setDeviceManagementWindow(DeviceManagementWindow window) {
        deviceManagementWindow = window;
    }

    /**
     * Enable or disable usage monitoring globally (called when usage-monitor extension is toggled).
     */
    public static void setUsageMonitoringEnabled(boolean enabled) {
        usageMonitoringEnabled = enabled;
        if (!enabled) {
            // Clear/reset all managers when monitoring is disabled
            for (DeviceUsageManager manager : managers.values()) {
                manager.reset();
            }
        }
    }

    /**
     * Check if usage monitoring is currently enabled.
     */
    public static boolean isUsageMonitoringEnabled() {
        return usageMonitoringEnabled;
    }

    /**
     * Register a driver for usage monitoring without immediate decoration.
     * Creates and stores DeviceUsageManager for the driver.
     * Actual decoration is handled by DriverManager when usage-monitor extension is active.
     *
     * @param driver driver to register
     * @param name   name under which manager will be registered in UI
     */
    public static void registerDriver(VisitableDriver driver, String name) {
        if (driver == null || name == null) {
            return;
        }

        DeviceUsageManager mgr = new DeviceUsageManager();
        String key = name;
        managers.put(key, mgr);
        driverToManagerMap.put(driver, mgr);

        try {
            if (deviceManagementWindow != null) {
                deviceManagementWindow.registerManager(mgr, key);
            }
        } catch (Throwable t) {
            // ignored
        }
    }

    /**
     * Get the DeviceUsageManager associated with a driver.
     *
     * @param driver driver to get manager for
     * @return DeviceUsageManager or null if not registered
     */
    public static DeviceUsageManager getManagerForDriver(VisitableDriver driver) {
        return driverToManagerMap.get(driver);
    }

    /**
     * Create a DeviceUsageManager, decorate the provided driver and register manager in UI under given name.
     * @deprecated Use registerDriver() instead for OCP-compliant extension handling.
     *
     * @param driver driver to decorate
     * @param name   name under which manager will be registered in UI
     * @return decorated driver
     */
    @Deprecated
    public static VisitableDriver decorateAndRegister(VisitableDriver driver, String name) {
        DeviceUsageManager mgr = new DeviceUsageManager();
        VisitableDriver decorated = new DeviceUsageDriverDecorator(driver, mgr);

        String key = (name != null) ? name : decorated.toString();
        managers.put(key, mgr);

        try {
            if (deviceManagementWindow != null) {
                deviceManagementWindow.registerManager(mgr, key);
            }
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

