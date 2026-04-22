package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;

public class ToggleExtensionOptionListener implements ActionListener {
    private DriverManager driverManager;
    private Job2dDriver extension;

    public ToggleExtensionOptionListener(Job2dDriver extension, DriverManager driverManager) {
        this.driverManager = driverManager;
        this.extension = extension;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JCheckBoxMenuItem) {
            JCheckBoxMenuItem checkBox = (JCheckBoxMenuItem) source;
            if (checkBox.getState()) {
                driverManager.addExtension(extension);
            } else {
                driverManager.removeExtension(extension);
            }
        }
    }
}