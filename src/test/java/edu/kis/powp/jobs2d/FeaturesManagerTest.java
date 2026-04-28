package edu.kis.powp.jobs2d;

import java.util.List;

import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.FeaturesManager;
import edu.kis.powp.jobs2d.features.IFeature;

public class FeaturesManagerTest {

    public static void main(String[] args) {
        testRegisterAndGetFeatures();
        System.out.println("FeaturesManagerTest passed.");
    }

    public static void testRegisterAndGetFeatures() {
        FeaturesManager.registerFeature(new CommandsFeature());
        FeaturesManager.registerFeature(new DrawerFeature());
        FeaturesManager.registerFeature(new DriverFeature());

        List<IFeature> features = FeaturesManager.getRegisteredFeatures();

        if (features.size() != 3) {
            throw new AssertionError("Expected 3 features, got " + features.size());
        }
        boolean hasCommands = features.stream().anyMatch(f -> f.getName().equals("Commands"));
        boolean hasDrawer = features.stream().anyMatch(f -> f.getName().equals("Drawer"));
        boolean hasDriver = features.stream().anyMatch(f -> f.getName().equals("Driver"));

        if (!hasCommands || !hasDrawer || !hasDriver) {
            throw new AssertionError("Not all expected features found");
        }
    }
}