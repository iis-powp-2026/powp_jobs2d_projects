package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.drivers.DriverManager;

import java.util.logging.Logger;

public class UsageLogger extends DriverManager {

    private static final Logger logger = Logger.getLogger("global");

    public void usageChanged(double total, double operating) {
        logger.info("Total: " + total + " | Operating: " + operating);
    }
}