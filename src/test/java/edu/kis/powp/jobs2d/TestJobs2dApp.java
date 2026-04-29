package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.drivers.RealTimeDriver;
import edu.kis.powp.jobs2d.drivers.RecordingDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.logger.TrackingLoggerDriver;
import edu.kis.powp.jobs2d.drivers.transformations.*;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.events.SelectLoadRecordedMacroOptionListener;
import edu.kis.powp.jobs2d.events.SelectClearPanelOptionListener;
import edu.kis.powp.jobs2d.features.RecordingFeature;
import edu.kis.powp.jobs2d.events.SelectToggleRecordingOptionListener;
import edu.kis.powp.jobs2d.events.SelectClearRecordingOptionListener;
import edu.kis.powp.jobs2d.features.FeaturesManager;
import edu.kis.powp.jobs2d.drivers.ClickDriver;

public class TestJobs2dApp {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Setup test concerning preset figures in context.
     * 
     * @param application Application context.
     */
    private static void setupPresetTests(Application application) {
        SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
                DriverFeature.getDriverManager());
        SelectTestFigure2OptionListener selectTestFigure2OptionListener = new SelectTestFigure2OptionListener(
                DriverFeature.getDriverManager());

        application.addTest("Figure Joe 1", selectTestFigureOptionListener);
        application.addTest("Figure Joe 2", selectTestFigure2OptionListener);
    }

    /**
     * Setup test using driver commands in context.
     * 
     * @param application Application context.
     */
    private static void setupCommandTests(Application application) {
        application.addTest("Load secret command", new SelectLoadSecretCommandOptionListener());
        application.addTest("Load immutable rectangle command", new SelectLoadImmutableRectangleCommandOptionListener());

        application.addTest("Load kite command", new SelectLoadKiteCommandOptionListener());
        application.addTest("Load recorded macro", new SelectLoadRecordedMacroOptionListener());

        application.addTest("Clear panel", new SelectClearPanelOptionListener());
        application.addTest("Run command", new SelectRunCurrentCommandOptionListener(DriverFeature.getDriverManager()));
        application.addTest("Count current command", new SelectCountCommandsOptionListener());

        RecordingDriver rec = RecordingFeature.getRecordingDriver();
        boolean initial = rec.isRecordingEnabled();

        application.addComponentMenuElementWithCheckBox(
                DriverFeature.class,
                "Recording",
                new SelectToggleRecordingOptionListener(rec),
                initial
        );

        application.addComponentMenuElement(
                DriverFeature.class,
                "Clear recording",
                new SelectClearRecordingOptionListener()
        );
    }

    /**
     * Setup driver manager, and set default Job2dDriver for application.
     * 
     * @param application Application context.
     */
    private static void setupDrivers(Application application) {
        Job2dDriver TrackingLoggerDriver = new TrackingLoggerDriver();
        DriverFeature.addDriver("Tracking Logger driver", TrackingLoggerDriver);

        DrawPanelController drawerController = DrawerFeature.getDrawerController();
        Job2dDriver driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        DriverFeature.addDriver("Line Simulator", driver);
        DriverFeature.getDriverManager().setCurrentDriver(driver);

        driver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
        DriverFeature.addDriver("Special line Simulator", driver);
        DriverFeature.updateDriverInfo();

        CoordinateTransformer scale = new ScaleTransformer(2.0, 2.0);
        Job2dDriver scaledDriver = new TransformingDriver(driver, scale, "Transform: Scaled 2x");
        DriverFeature.addDriver(scaledDriver.toString(), scaledDriver);

        CoordinateTransformer scaleDown = new ScaleTransformer(0.5, 0.5);
        Job2dDriver scaledDownDriver = new TransformingDriver(driver, scaleDown, "Transform: Scaled 0.5x");
        DriverFeature.addDriver(scaledDownDriver.toString(), scaledDownDriver);

        CoordinateTransformer flip = new FlipTransformer(false, true);
        Job2dDriver flippedDriver = new TransformingDriver(driver, flip, "Transform: Flipped Y");
        DriverFeature.addDriver(flippedDriver.toString(), flippedDriver);

        CoordinateTransformer rotate = new RotateTransformer(45.0);
        Job2dDriver rotatedDriver = new TransformingDriver(driver, rotate, "Transform: Rotated 45 degrees");
        DriverFeature.addDriver(rotatedDriver.toString(), rotatedDriver);

        Job2dDriver scaledAndRotatedDriver = new TransformingDriver(scaledDriver, rotate, "Transform: Scaled 2x & Rotated 45");
        DriverFeature.addDriver(scaledAndRotatedDriver.toString(), scaledAndRotatedDriver);

        driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        Job2dDriver animatedDriver = new RealTimeDriver(driver, 10, 10, "Real-Time Driver 1x speed");
        DriverFeature.addDriver(animatedDriver.toString(), animatedDriver);

        animatedDriver = new RealTimeDriver(driver, 5, 5, "Real-Time Driver 2x speed");
        DriverFeature.addDriver(animatedDriver.toString(), animatedDriver);

        animatedDriver = new RealTimeDriver(driver, 1, 1, "Real-Time Driver 10x speed");
        DriverFeature.addDriver(animatedDriver.toString(), animatedDriver);
    }

    private static void setupWindows(Application application) {

        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getDriverCommandManager());
        application.addWindowComponent("Command Manager", commandManager);

        CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(
                commandManager);
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(windowObserver);
    }

    /**
     * Setup menu for adjusting logging settings.
     * 
     * @param application Application context.
     */
    private static void setupLogger(Application application) {

        application.addComponentMenu(Logger.class, "Logger", 0);
        application.addComponentMenuElement(Logger.class, "Clear log",
                (ActionEvent e) -> application.flushLoggerOutput());
        application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
        application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
        application.addComponentMenuElement(Logger.class, "Warning level",
                (ActionEvent e) -> logger.setLevel(Level.WARNING));
        application.addComponentMenuElement(Logger.class, "Severe level",
                (ActionEvent e) -> logger.setLevel(Level.SEVERE));
        application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
    }

    private static void setupMouseHandler(Application application) {
        new ClickDriver(application.getFreePanel());
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application("Jobs 2D");

                // Przykład użycia automatycznego zarządzania funkcjami (features management)
                // Zarejestruj funkcje, które mają być automatycznie skonfigurowane
                FeaturesManager.registerFeature(new DrawerFeature());
                FeaturesManager.registerFeature(new CommandsFeature());
                FeaturesManager.registerFeature(new DriverFeature());

                // Automatycznie skonfiguruj wszystkie zarejestrowane funkcje
                // To zastępuje ręczne wywołania setup dla każdej funkcji
                FeaturesManager.setupAllFeatures(app);

                setupDrivers(app);
                RecordingFeature.setup(DriverFeature.getDriverManager());
                setupPresetTests(app);
                setupCommandTests(app);
                setupLogger(app);
                setupWindows(app);
                setupMouseHandler(app);

                app.setVisibility(true);
            }
        });
    }

}
