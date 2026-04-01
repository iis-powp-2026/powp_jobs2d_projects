package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager for automatic features management.
 * Allows registering features and setting them up automatically.
 */
public class FeaturesManager {

    private static final List<IFeature> features = new ArrayList<>();

    /**
     * Register a feature to be managed.
     * @param feature The feature to register.
     */
    public static void registerFeature(IFeature feature) {
        features.add(feature);
    }

    /**
     * Setup all registered features with the given application.
     * @param application The application context.
     */
    public static void setupAllFeatures(Application application) {
        for (IFeature feature : features) {
            try {
                feature.setup(application);
                System.out.println("Feature '" + feature.getName() + "' set up successfully.");
            } catch (Exception e) {
                System.err.println("Failed to setup feature '" + feature.getName() + "': " + e.getMessage());
            }
        }
    }

    /**
     * Get the list of registered features.
     * @return List of features.
     */
    public static List<IFeature> getRegisteredFeatures() {
        return new ArrayList<>(features);
    }
}