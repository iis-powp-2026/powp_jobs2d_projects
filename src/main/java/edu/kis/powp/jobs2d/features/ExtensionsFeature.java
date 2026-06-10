package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverChainUtils;
import edu.kis.powp.jobs2d.drivers.optionals.DecoratorDriver;
import edu.kis.powp.jobs2d.drivers.optionals.RecordingDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.events.SelectClearRecordingOptionListener;
import edu.kis.powp.jobs2d.events.SelectToggleRecordingOptionListener;

import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Feature that provides optional extensions (add-ons) working independently
 * of the selected driver. Extensions are toggled via checkboxes in the
 * Extensions menu.
 * Extensions form a decorator chain. Each extension can be enabled/disabled
 * independently — disabling removes it from the chain like a linked-list node
 * removal, connecting its predecessor directly to its successor.
 */
public class ExtensionsFeature implements IFeature {

    private static Application app;
    private static final Map<String, DecoratorDriver> activeExtensions = new LinkedHashMap<>();

    @Override
    public void setup(Application application) {
        app = application;
        app.addComponentMenu(ExtensionsFeature.class, "Extensions");
    }

    @Override
    public String getName() {
        return "Extensions";
    }

    /**
     * Registers a generic extension in the Extensions menu with a toggle checkbox.
     * The factory receives the current driver and returns a new decorator wrapping it.
     * Enabling inserts the decorator at the top of the chain; disabling removes it
     * from anywhere in the chain (linked-list removal).
     *
     * @param name    Label shown in the Extensions menu.
     * @param factory Constructor reference, e.g. {@code LoggingExtensionDriver::new}.
     */
    public static void addExtension(String name, Function<VisitableDriver, DecoratorDriver> factory) {
        app.addComponentMenuElementWithCheckBox(
                ExtensionsFeature.class,
                name,
                (ActionEvent e) -> {
                    javax.swing.AbstractButton btn = (javax.swing.AbstractButton) e.getSource();
                    if (btn.isSelected()) {
                        enableExtension(name, factory);
                    } else {
                        disableExtension(name);
                    }
                    DriverFeature.updateDriverInfo();
                },
                false
        );
    }

    /**
     * Add Recording extension checkbox and clear button to the Extensions menu.
     * Must be called after RecordingFeature.setup().
     */
    public static void setupRecordingExtension() {
        RecordingDriver rec = RecordingFeature.getRecordingDriver();
        boolean initial = rec.isRecordingEnabled();

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

    /**
     * Inserts the extension decorator at the top of the current driver chain.
     */
    private static void enableExtension(String name, Function<VisitableDriver, DecoratorDriver> factory) {
        if (activeExtensions.containsKey(name)) {
            return;
        }
        VisitableDriver current = DriverFeature.getDriverManager().getCurrentDriver();
        DecoratorDriver decorator = factory.apply(current);
        activeExtensions.put(name, decorator);
        DriverFeature.getDriverManager().setCurrentDriver(decorator);
    }

    /**
     * Removes the extension decorator from anywhere in the decorator chain,
     * connecting its predecessor directly to its successor (linked-list removal).
     * Works correctly even when other extensions are nested around it.
     */
    private static void disableExtension(String name) {
        DecoratorDriver decorator = activeExtensions.remove(name);
        if (decorator == null) {
            return;
        }
        VisitableDriver current = DriverFeature.getDriverManager().getCurrentDriver();
        VisitableDriver newRoot = DriverChainUtils.removeFromChain(current, decorator);
        if (newRoot != current) {
            DriverFeature.getDriverManager().setCurrentDriver(newRoot);
        }
        // If newRoot == current, the decorator was in the middle of the chain —
        // its predecessor's target was already updated by removeFromChain.
    }
}
