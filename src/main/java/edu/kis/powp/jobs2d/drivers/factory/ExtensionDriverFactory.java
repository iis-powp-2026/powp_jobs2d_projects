package edu.kis.powp.jobs2d.drivers.factory;

import edu.kis.powp.jobs2d.drivers.BoundsDriver;
import edu.kis.powp.jobs2d.drivers.RealTimeDriver;
import edu.kis.powp.jobs2d.drivers.optionals.LoggingExtensionDriver;
import edu.kis.powp.jobs2d.drivers.transformations.RotateTransformer;
import edu.kis.powp.jobs2d.drivers.transformations.ScaleTransformer;
import edu.kis.powp.jobs2d.drivers.transformations.TransformingDriver;

/**
 * Factory for creating drivers for usage as extensions.
 */
public class ExtensionDriverFactory {
    public static LoggingExtensionDriver createLoggingDriver() {
        return new LoggingExtensionDriver(null);
    }
    public static TransformingDriver createScaleDriver(double scale, String name) {
        return new TransformingDriver(null, new ScaleTransformer(scale, scale), name);
    }
    public static TransformingDriver createScaleDriver(double scaleX, double scaleY, String name) {
        return new TransformingDriver(null, new ScaleTransformer(scaleX, scaleY), name);
    }
    public static TransformingDriver createRotateDriver(double rotationDegrees, String name) {
        return new TransformingDriver(null, new RotateTransformer(rotationDegrees), name);
    }
    public static RealTimeDriver createRealTimeDriver(int speed, String name) {
        return new RealTimeDriver(null, speed, speed, name);
    }
    public static BoundsDriver createBoundsDriver() {
        return new BoundsDriver(null);
    }
}
