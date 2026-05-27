package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DeviceUsageDriverDecorator;
import edu.kis.powp.jobs2d.drivers.DeviceUsageManager;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.gui.DeviceManagementWindow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Feature that provides per-driver DeviceUsageManager instances and a window to manage them.
 */
public class DeviceUsageFeature implements IFeature {

    private static DeviceManagementWindow deviceManagementWindow;
    private static final Map<String, DeviceUsageManager> managers = new ConcurrentHashMap<>();

    @Override
    public void setup(Application application) {
        deviceManagementWindow = new DeviceManagementWindow();

        for (Map.Entry<String, DeviceUsageManager> e : managers.entrySet()) {
            deviceManagementWindow.registerManager(e.getValue(), e.getKey());
        }

        application.addComponentMenu(DeviceUsageFeature.class, "Device Usage");
        application.addComponentMenuElement(DeviceUsageFeature.class, "Open Device Manager",
                (e) -> {
                    if (deviceManagementWindow != null) {
                        deviceManagementWindow.setVisible(true);
                    }
                });
    }

    /**
     * Create a DeviceUsageManager for given driver, wrap driver into DeviceUsageDriverDecorator and register the manager
     * under provided name (shown in DeviceManagementWindow).
     *
     * @param driver driver to decorate
     * @param name   name to register the manager under (should be unique or descriptive)
     * @return decorated driver
     */
    public static VisitableDriver decorateDriver(VisitableDriver driver, String name) {
        DeviceUsageManager mgr = new DeviceUsageManager();
        VisitableDriver decorated = new DeviceUsageDriverDecorator(driver, mgr);

        String key = (name != null) ? name : decorated.toString();
        managers.put(key, mgr);

        if (deviceManagementWindow != null) {
            deviceManagementWindow.registerManager(mgr, key);
        }

        return decorated;
    }

    /**
     * Backward-compatible decorateDriver: use driver's toString() as name.
     */
    public static VisitableDriver decorateDriver(VisitableDriver driver) {
        return decorateDriver(driver, driver != null ? driver.toString() : "unknown-driver");
    }

    public static DeviceManagementWindow getDeviceManagementWindow() {
        return deviceManagementWindow;
    }

    @Override
    public String getName() {
        return "Device Usage";
    }
}