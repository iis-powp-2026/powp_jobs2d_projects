package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;

/**
 * Interface for features that can be automatically managed.
 */
public interface IFeature {

    /**
     * Setup the feature with the given application context.
     * @param application The application context.
     */
    void setup(Application application);

    /**
     * Get the name of the feature.
     * @return Feature name.
     */
    String getName();
}