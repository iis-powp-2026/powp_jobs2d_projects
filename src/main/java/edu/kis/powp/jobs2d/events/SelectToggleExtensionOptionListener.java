package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.gui.DeviceManagementWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Toggles a named driver extension on/off inside {@link DriverManager}.
 * Special handling for "usage-monitor" extension to show/hide Device Usage window.
 */
public class SelectToggleExtensionOptionListener implements ActionListener {

    private final DriverManager driverManager;
    private final String extensionName;
    private final VisitableDriver extension;
    private DeviceManagementWindow deviceManagementWindow;

    /**
     * @param driverManager   The manager that owns the extension registry.
     * @param extensionName   Stable key used to add/remove the extension.
     * @param extension       The extension driver instance to toggle.
     * @param initiallyActive If {@code true} the extension is registered immediately.
     */
    public SelectToggleExtensionOptionListener(DriverManager driverManager, String extensionName, VisitableDriver extension, boolean initiallyActive) {
        this.driverManager = driverManager;
        this.extensionName = extensionName;
        this.extension = extension;

        if (initiallyActive) {
            driverManager.addExtension(extensionName, extension);
        }
    }

    /**
     * Set Device Management Window reference for special handling of usage-monitor extension.
     */
    public void setDeviceManagementWindow(DeviceManagementWindow window) {
        this.deviceManagementWindow = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (driverManager.hasExtension(extensionName)) {
            driverManager.removeExtension(extensionName);

            // Disable usage monitoring when usage-monitor extension is disabled
            if ("usage-monitor".equals(extensionName)) {
                edu.kis.powp.jobs2d.drivers.DeviceUsageRegistrar.setUsageMonitoringEnabled(false);
            }

            // Hide Device Usage window when usage-monitor extension is disabled
            if ("usage-monitor".equals(extensionName) && deviceManagementWindow != null) {
                deviceManagementWindow.setVisible(false);
            }
        } else {
            driverManager.addExtension(extensionName, extension);

            // Enable usage monitoring when usage-monitor extension is enabled
            if ("usage-monitor".equals(extensionName)) {
                edu.kis.powp.jobs2d.drivers.DeviceUsageRegistrar.setUsageMonitoringEnabled(true);
            }

            // Show Device Usage window when usage-monitor extension is enabled
            if ("usage-monitor".equals(extensionName) && deviceManagementWindow != null) {
                deviceManagementWindow.setVisible(true);
            }
        }
    }
}