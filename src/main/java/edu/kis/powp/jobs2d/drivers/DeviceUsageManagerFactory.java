package edu.kis.powp.jobs2d.drivers;

/**
 * Factory for creating DeviceUsageManager instances with predefined settings.
 */
public class DeviceUsageManagerFactory {
    public static final double DEFAULT_MAX_OPERATIONAL_USAGE = 10000.0;

    /**
     * Creates a DeviceUsageManager with the default operational usage level.
     * @return manager with default settings
     */
    public static DeviceUsageManager createDefaultManager() {
        return new DeviceUsageManager(DEFAULT_MAX_OPERATIONAL_USAGE);
    }

    /**
     * Creates a DeviceUsageManager with a custom operational usage level.
     * @param maxUsage custom limit
     * @return manager with custom settings
     */
    public static DeviceUsageManager createManager(double maxUsage) {
        return new DeviceUsageManager(maxUsage);
    }
}
