package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DeviceUsageManager;
import edu.kis.powp.jobs2d.gui.DeviceManagementWindow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UI feature that manages the DeviceManagementWindow and registers managers for UI display.
 */
public class DeviceUsageFeature implements IFeature {

    private static DeviceManagementWindow deviceManagementWindow;
    private static final Map<String, DeviceUsageManager> pendingManagers = new ConcurrentHashMap<>();

    @Override
    public void setup(Application application) {
        deviceManagementWindow = new DeviceManagementWindow();
        for (Map.Entry<String, DeviceUsageManager> e : pendingManagers.entrySet()) {
            deviceManagementWindow.registerManager(e.getValue(), e.getKey());
        }
        pendingManagers.clear();

        application.addWindowComponent("Device Usage", deviceManagementWindow);

        application.addComponentMenu(DeviceUsageFeature.class, "Device Usage");
        application.addComponentMenuElement(DeviceUsageFeature.class, "Open Device Manager",
                (e) -> deviceManagementWindow.HideIfVisibleAndShowIfHidden());
    }

    /**
     * Register a DeviceUsageManager in the UI. If the UI is not yet created, store it for later registration.
     *
     * @param manager manager to register
     * @param name    display name
     */
    public static void registerManager(DeviceUsageManager manager, String name) {
        if (manager == null || name == null) return;
        if (deviceManagementWindow != null) {
            deviceManagementWindow.registerManager(manager, name);
        } else {
            pendingManagers.put(name, manager);
        }
    }

    @Override
    public String getName() {
        return "Device Usage";
    }

    public static DeviceManagementWindow getDeviceManagementWindow() {
        return deviceManagementWindow;
    }
}