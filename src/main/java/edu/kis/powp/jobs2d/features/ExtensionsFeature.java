package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.BoundsDriver;
import edu.kis.powp.jobs2d.drivers.RealTimeDriver;
import edu.kis.powp.jobs2d.drivers.optionals.DecoratorDriver;
import edu.kis.powp.jobs2d.drivers.optionals.LoggingExtensionDriver;
import edu.kis.powp.jobs2d.drivers.optionals.RecordingDriver;
import edu.kis.powp.jobs2d.drivers.transformations.RotateTransformer;
import edu.kis.powp.jobs2d.drivers.transformations.ScaleTransformer;
import edu.kis.powp.jobs2d.drivers.transformations.TransformingDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.events.SelectClearRecordingOptionListener;
import edu.kis.powp.jobs2d.events.SelectToggleRecordingOptionListener;
import edu.kis.powp.observer.Subscriber;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class ExtensionsFeature implements IFeature, Subscriber {

    private static Application app;
    private static final Set<DecoratorDriver> extensionOrder = new LinkedHashSet<>();
    private static final Set<DecoratorDriver> activeExtensions = new HashSet<>();

    @Override
    public void setup(Application application) {
        app = application;
        app.addComponentMenu(ExtensionsFeature.class, "Extensions");

        DriverFeature.getDriverManager()
                .getChangePublisher()
                .addSubscriber(this);
    }

    @Override
    public String getName() {
        return "Extensions";
    }

    public static void addExtension(String name, DecoratorDriver driver) {
        extensionOrder.add(driver);
        app.addComponentMenuElementWithCheckBox(
            ExtensionsFeature.class,
            name,
            (ActionEvent e) -> {
                AbstractButton btn = (AbstractButton) e.getSource();
                if (btn.isSelected()) {
                    enableExtension(driver);
                } else {
                    disableExtension(driver);
                }
                DriverFeature.updateDriverInfo();
            },
            false
        );
    }

    public static void setupRecordingExtension() {
        RecordingDriver rec = RecordingFeature.getRecordingDriver();
        boolean initial = rec.isRecordingEnabled();

        ExtensionsFeature.addExtension("Tracking Logger", new LoggingExtensionDriver(null));
        ExtensionsFeature.addExtension("2x scale", new TransformingDriver(null, new ScaleTransformer(2.0, 2.0), "2x scale"));
        ExtensionsFeature.addExtension("0.5x scale", new TransformingDriver(null, new ScaleTransformer(0.5, 0.5), "0.5x scale"));
        ExtensionsFeature.addExtension("Flip Y", new TransformingDriver(null, new ScaleTransformer(1., -1.), "Y Flip"));
        ExtensionsFeature.addExtension("Rotated 45 deg", new TransformingDriver(null, new RotateTransformer(45.0), "Rot 45 deg"));
        ExtensionsFeature.addExtension("Real-Time Driver 2x speed", new RealTimeDriver(null, 5, 5, "Real-Time Driver 2x speed"));
        ExtensionsFeature.addExtension("Boundaries", new BoundsDriver(null));
        app.addComponentMenuElementWithCheckBox(
                ExtensionsFeature.class,
                "Recording",
                new SelectToggleRecordingOptionListener(rec),
                initial
        );

        app.addComponentMenuElement(
                ExtensionsFeature.class,
                "Clear recording",
                new SelectClearRecordingOptionListener()
        );
    }

    private static void enableExtension(DecoratorDriver driver) {
        if (activeExtensions.contains(driver)) {
            return;
        }
        activeExtensions.add(driver);
        rebuild();
    }

    private static void disableExtension(DecoratorDriver driver) {
        if (!activeExtensions.remove(driver)) {
            return;
        }
        rebuild();
    }

    @Override
    public void update() {
        VisitableDriver currentDriver = DriverFeature.getDriverManager().getCurrentDriver();
        if (currentDriver instanceof DecoratorDriver && activeExtensions.contains(currentDriver)) {
            return;
        }
        rebuild(currentDriver);
    }

    private static void rebuild() {
        VisitableDriver top = DriverFeature.getDriverManager().getCurrentDriver();
        while (top instanceof DecoratorDriver && extensionOrder.contains(top)) {
            top = ((DecoratorDriver) top).getTarget();
        }
        rebuild(top);
    }

    /**
     * Przebudowuje wszystkie rozszerzenia na podstawie aktualnego drivera.
     *
     * @param top Pierwszy driver, który nie jest rozszerzeniem.
     */
    private static void rebuild(VisitableDriver top) {
        DecoratorDriver first = null, previous = null;
        for (DecoratorDriver extension : extensionOrder) {
            if (!activeExtensions.contains(extension)) {
                continue;
            }
            if (previous != null) {
                previous.setTarget(extension);
            } else {
                first = extension;
            }
            previous = extension;
        }
        if (first != null) {
            previous.setTarget(top);
            DriverFeature.getDriverManager().setCurrentDriver(first);
        } else {
            DriverFeature.getDriverManager().setCurrentDriver(top);
        }
        DriverFeature.updateDriverInfo();
    }
}
