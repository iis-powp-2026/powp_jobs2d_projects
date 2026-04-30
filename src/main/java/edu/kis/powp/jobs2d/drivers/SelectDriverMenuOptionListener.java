package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class SelectDriverMenuOptionListener implements ActionListener {
    private DriverManager driverManager;
    private VisitableDriver driver = null;

    public SelectDriverMenuOptionListener(VisitableDriver driver, DriverManager driverManager) {
        this.driverManager = driverManager;
        this.driver = driver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        driverManager.setCurrentDriver(driver);
    }
}
