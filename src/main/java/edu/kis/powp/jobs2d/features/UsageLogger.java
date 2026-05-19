package edu.kis.powp.jobs2d.features;

import edu.kis.powp.observer.Subscriber;
import java.util.logging.Logger;

public class UsageLogger implements Subscriber {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final UsageMonitoringDriver monitoringDriver;

    public UsageLogger(UsageMonitoringDriver monitoringDriver) {
        this.monitoringDriver = monitoringDriver;
    }

    @Override
    public void update() {
        logger.info(
                "Total distance: " + monitoringDriver.getTotalDistance() +
                        ", Operation distance: " + monitoringDriver.getOperationDistance()
        );
    }
}