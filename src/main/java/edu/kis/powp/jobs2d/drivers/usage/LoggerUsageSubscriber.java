package edu.kis.powp.jobs2d.drivers.usage;

import java.util.logging.Logger;

public class LoggerUsageSubscriber implements UsageSubscriber {
    private static final Logger logger = Logger.getLogger("global");

    @Override
    public void update(double headDistance, double operatingDistance) {
        logger.info("head distance: " + (int)Math.round(headDistance) + " units");
        logger.info("op. distance: " + (int)Math.round(operatingDistance) + " units");
    }
}