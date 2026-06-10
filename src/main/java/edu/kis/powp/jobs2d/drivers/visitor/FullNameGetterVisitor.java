package edu.kis.powp.jobs2d.drivers.visitor;


import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.optionals.AbstractDecoratorDriver;
import edu.kis.powp.jobs2d.drivers.optionals.TrackingLoggerDriver;
import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;

public class FullNameGetterVisitor implements DriverVisitor {

    private StringBuilder builder = new StringBuilder("Full name: ");

    @Override
    public void visit(CompositeDriver driver) {
        builder.append(driver.toString()).append(" ");

        for (VisitableDriver vd : driver.getDrivers()) {
            vd.accept(this);
        }
    }

    @Override
    public void visit(LineDriverAdapter adapter) {
        builder.append(adapter.toString());
    }

    @Override
    public void visit(TrackingLoggerDriver driver) {
        builder.append(driver.toString());
    }

    /**
     * Handles all AbstractDecoratorDriver subclasses (LoggingExtensionDriver,
     * RecordingDriver, and any future decorators) in one place.
     * Appends the decorator's label and recurses into the wrapped driver.
     */
    @Override
    public void visit(AbstractDecoratorDriver driver) {
        builder.append(driver.toString());
        driver.getTarget().accept(this);
    }

    public String getAndResetFullName() {
        String ret = builder.toString();
        builder = new StringBuilder();
        return ret;
    }
}
