package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtensionToggleListener implements ActionListener {

    private final DriverManager driverManager;
    private final VisitableDriver extension;
    private boolean enabled = false;

    public ExtensionToggleListener(DriverManager driverManager, VisitableDriver extension) {
        this.driverManager = driverManager;
        this.extension = extension;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (enabled) {
            driverManager.removeExtension(extension);
            enabled = false;
        } else {
            driverManager.addExtension(extension);
            enabled = true;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }
}
