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
    private static final CompositeDriver recordingComposite = new CompositeDriver("Recording Composite");
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
        if (!recordingComposite.getDrivers().isEmpty()) return;

        RecordingDriver rec = RecordingFeature.getRecordingDriver();
        boolean initial = rec.isRecordingEnabled();

        recordingComposite.addDriver(rec);

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
        if (currentDriver == recordingComposite) {
            return;
        }
        rebuild(currentDriver);
    }

    private static void rebuild() {
        VisitableDriver top = DriverFeature.getDriverManager().getCurrentDriver();
        while (top == recordingComposite || extensionOrder.contains(top)) {
            top = getNextInChain(top);
        }
        rebuild(top);
    }

    private static VisitableDriver getNextInChain(VisitableDriver current) {
        if (current == recordingComposite && !recordingComposite.getDrivers().isEmpty()) {
            return recordingComposite.getDrivers().get(recordingComposite.getDrivers().size() - 1);
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
        recordingComposite.getDrivers().clear();
        recordingComposite.getDrivers().add(RecordingFeature.getRecordingDriver());
        if (first != null) {
            previous.setTarget(top);
            recordingComposite.getDrivers().add(first);
        } else {
            recordingComposite.getDrivers().add(top);
        }
        DriverFeature.getDriverManager().setCurrentDriver(recordingComposite);
        DriverFeature.updateDriverInfo();
    }
}
