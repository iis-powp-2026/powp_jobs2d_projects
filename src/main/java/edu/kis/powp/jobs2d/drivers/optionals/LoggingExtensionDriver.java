package edu.kis.powp.jobs2d.drivers.optionals;

import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

import java.util.logging.Logger;


public class LoggingExtensionDriver extends AbstractDecoratorDriver {

    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private int operationCount = 0;

    public LoggingExtensionDriver(VisitableDriver target) {
        super(target);
    }

    @Override
    public synchronized void setPosition(int x, int y) {
        logger.info("Position set to x: " + x + ", y: " + y + " [" + operationCount + "]");
        super.setPosition(x, y);
    }

    @Override
    public synchronized void operateTo(int x, int y) {
        operationCount++;
        logger.info("Operate to x: " + x + ", y: " + y + " [" + operationCount + "]");
        super.operateTo(x, y);
    }

    @Override
    public String toString() {
        return "Tracking Logger -> " + getTarget();
    }
}
