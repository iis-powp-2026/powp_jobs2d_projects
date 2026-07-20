package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.optionals.DecoratorDriver;
import edu.kis.powp.jobs2d.drivers.optionals.RecordingDriver;
import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.events.SelectClearRecordingOptionListener;
import edu.kis.powp.jobs2d.events.SelectToggleRecordingOptionListener;
import edu.kis.powp.observer.Subscriber;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ExtensionsFeature implements IFeature, Subscriber {

    private static Application app;
    private static final CompositeDriver rootComposite = new CompositeDriver("Root Composite");
    private static final Set<DecoratorDriver> extensionOrder = new LinkedHashSet<>();
    private static final Set<DecoratorDriver> activeExtensions = new HashSet<>();
    private static final Set<VisitableDriver> activeNonDecoratorExtensions = new LinkedHashSet<>();

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

    public static void addNonDecoratorExtension(String name, VisitableDriver driver) {
        addNonDecoratorExtension(name, driver, true);
    }

    public static void addNonDecoratorExtension(String name, VisitableDriver driver, boolean toggleable) {
        if (toggleable) {
            app.addComponentMenuElementWithCheckBox(
                ExtensionsFeature.class,
                name,
                (ActionEvent e) -> {
                    AbstractButton btn = (AbstractButton) e.getSource();
                    if (btn.isSelected()) {
                        activeNonDecoratorExtensions.add(driver);
                    } else {
                        activeNonDecoratorExtensions.remove(driver);
                    }
                    rebuild();
                    DriverFeature.updateDriverInfo();
                },
                false
            );
        } else {
            activeNonDecoratorExtensions.add(driver);
            rebuild();
        }
    }

    public static void setupRecordingExtension() {
        RecordingDriver rec = RecordingFeature.getRecordingDriver();

        addNonDecoratorExtension("Recording", rec, false);

        app.addComponentMenuElementWithCheckBox(
                ExtensionsFeature.class,
                "Recording",
                new SelectToggleRecordingOptionListener(rec),
                rec.isRecordingEnabled()
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
        if (currentDriver == rootComposite) {
            return;
        }
        rebuild(currentDriver);
    }

    private static void rebuild() {
        VisitableDriver top = DriverFeature.getDriverManager().getCurrentDriver();
        while (top == rootComposite || extensionOrder.contains(top)) {
            top = getNextInChain(top);
        }
        rebuild(top);
    }

    private static VisitableDriver getNextInChain(VisitableDriver current) {
        if (current == rootComposite && !rootComposite.getDrivers().isEmpty()) {
            return rootComposite.getDrivers().get(rootComposite.getDrivers().size() - 1);
        } else if (current instanceof DecoratorDriver) {
            return ((DecoratorDriver) current).getTarget();
        }
        return null;
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
        rootComposite.getDrivers().clear();
        for (VisitableDriver leaf : activeNonDecoratorExtensions) {
            rootComposite.addDriver(leaf);
        }
        if (first != null) {
            previous.setTarget(top);
            rootComposite.addDriver(first);
        } else {
            rootComposite.addDriver(top);
        }
        DriverFeature.getDriverManager().setCurrentDriver(rootComposite);
        DriverFeature.updateDriverInfo();
    }
}
