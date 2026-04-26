package edu.kis.powp.jobs2d.drivers.usage;

import edu.kis.powp.observer.Subscriber;
import java.util.logging.Logger;

public class LoggerUsageMonitorSubscriber implements Subscriber {
    private final UsageMonitorDriver monitorDriver;
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public LoggerUsageMonitorSubscriber(UsageMonitorDriver monitorDriver) {
        this.monitorDriver = monitorDriver;
    }

    @Override
    public void update() {
        logger.info("Head distance: " + monitorDriver.getHeadDistance() + " units, Op. distance: " + monitorDriver.getOperatingDistance() + " units");
    }
}
