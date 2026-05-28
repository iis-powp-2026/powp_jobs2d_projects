package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Toggles a named driver extension on/off inside {@link DriverManager}.
 * Additional behavior can be attached via onEnable and onDisable actions.
 */
public class SelectToggleExtensionOptionListener implements ActionListener {

    private final DriverManager driverManager;
    private final String extensionName;
    private final VisitableDriver extension;
    
    private Runnable onEnableAction = () -> {};
    private Runnable onDisableAction = () -> {};

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
     * Sets an action to be executed when the extension is enabled.
     */
    public void setOnEnableAction(Runnable onEnableAction) {
        this.onEnableAction = onEnableAction;
    }

    /**
     * Sets an action to be executed when the extension is disabled.
     */
    public void setOnDisableAction(Runnable onDisableAction) {
        this.onDisableAction = onDisableAction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (driverManager.hasExtension(extensionName)) {
            driverManager.removeExtension(extensionName);
            onDisableAction.run();
        } else {
            driverManager.addExtension(extensionName, extension);
            onEnableAction.run();
        }
    }
}