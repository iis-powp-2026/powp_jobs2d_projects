package edu.kis.powp.jobs2d;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.comparator.ComplexCommandComparator;
import edu.kis.powp.jobs2d.command.comparator.comparison_strategy.LineListComparator;
import edu.kis.powp.jobs2d.command.comparator.comparison_strategy.LineSetComparator;
import edu.kis.powp.jobs2d.command.comparator.comparison_strategy.ScaleIgnoreComparator;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.gui.CommandsHistoryWindow;
import edu.kis.powp.jobs2d.command.manager.CommandPreviewChangeObserver;
import edu.kis.powp.jobs2d.drivers.MouseClickToDriverCall;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.factory.ExtensionDriverFactory;
import edu.kis.powp.jobs2d.drivers.packet_composite.CompositeDriver;
import edu.kis.powp.jobs2d.drivers.transformations.*;
import edu.kis.powp.jobs2d.drivers.visitor.FullNameGetterVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        application.addTest("Deep copy of current command", new SelectDeepCopyCommandOptionListener());

        application.addTest("Check current command bounds", new SelectCheckCanvasBoundsOptionListener());
        application.addTest("Transform current command: Scale 2x",
                new SelectTransformCommandOptionListener(new ScaleTransformer(2.0, 2.0), "Scale 2x"));
        application.addTest("Transform current command: Scale 0.5x",
                new SelectTransformCommandOptionListener(new ScaleTransformer(0.5, 0.5), "Scale 0.5x"));
        application.addTest("Transform current command: Rotate 45 degrees",
                new SelectTransformCommandOptionListener(new RotateTransformer(45.0), "Rotate 45 degrees"));
        application.addTest("Transform current command: Flip Y",
                new SelectTransformCommandOptionListener(new FlipTransformer(false, true), "Flip Y"));
        application.addTest("FullNameGetter visitor test",
                new SelectFullNameGetterVisitorTestListener(new FullNameGetterVisitor()));
        application.addTest("Compare current command with previous command - LineList",
                new SelectCompareCommandsListener(new ComplexCommandComparator(new LineListComparator())));
        application.addTest("Compare current command with previous command - LineSet",
                new SelectCompareCommandsListener(new ComplexCommandComparator(new LineSetComparator())));
        application.addTest("Compare current command with previous command - ScaleIgnore",
                new SelectCompareCommandsListener(new ComplexCommandComparator(new ScaleIgnoreComparator())));
        application.addTest("Show commands history", new CommandsHistoryOptionListener());

    }

    /**
     * Setup extensions for application.
     */
    private static void setupExtensions() {
        ExtensionsFeature.addExtension("Tracking Logger", ExtensionDriverFactory.createLoggingDriver());
        ExtensionsFeature.addExtension("2x scale", ExtensionDriverFactory.createScaleDriver(2, "2x scale"));
        ExtensionsFeature.addExtension("0.5x scale", ExtensionDriverFactory.createScaleDriver(0.5, "0.5x scale"));
        ExtensionsFeature.addExtension("Flip Y", ExtensionDriverFactory.createScaleDriver(1, -1, "Y Flip"));
        ExtensionsFeature.addExtension("Rotated 45 deg", ExtensionDriverFactory.createRotateDriver(45.0, "Rot 45 deg"));
        ExtensionsFeature.addExtension("Real-Time Driver", ExtensionDriverFactory.createRealTimeDriver(5, "Real-Time Driver"));
        ExtensionsFeature.addExtension("Boundaries", ExtensionDriverFactory.createBoundsDriver());
        ExtensionsFeature.setupRecordingExtension();
    }

    /**
     * Setup driver manager, and set default VisitableDriver for application.
     * 
     * @param application Application context.
     */
    private static void setupDrivers(Application application) {
        DrawPanelController drawerController = DrawerFeature.getDrawerController();
        VisitableDriver driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        DriverFeature.addDriver("Line Simulator", driver);
        DriverFeature.getDriverManager().setCurrentDriver(driver);

        driver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
        DriverFeature.addDriver("Special line Simulator", driver);

        CoordinateTransformer scaleDown = new ScaleTransformer(0.5, 0.5);
        VisitableDriver scaledDownDriver = new TransformingDriver(driver, scaleDown, "Transform: Scaled 0.5x");

        CoordinateTransformer rotate = new RotateTransformer(45.0);
        VisitableDriver rotatedDriver = new TransformingDriver(driver, rotate, "Transform: Rotated 45 degrees");

        CompositeDriver chaosCompositeDriver = new CompositeDriver("Chaos Composite Driver");
        chaosCompositeDriver.addDriver(driver);
        chaosCompositeDriver.addDriver(rotatedDriver);
        chaosCompositeDriver.addDriver(scaledDownDriver);
        DriverFeature.addDriver(chaosCompositeDriver.toString(), chaosCompositeDriver);

        driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");

        UsageMonitoringDriver monitoredDriver = new UsageMonitoringDriver(driver);
        UsageLogger usageLogger = new UsageLogger(monitoredDriver);
        monitoredDriver.addSubscriber(usageLogger);
        DriverFeature.addDriver("Monitored", monitoredDriver);
    }

    private static void setupWindows(Application application) {
            
        DrawPanelController previewDrawPanelController = new DrawPanelController();
        VisitableDriver driver = new LineDriverAdapter(previewDrawPanelController, LineFactory.getBasicLine(), "basic");
        VisitableDriver canvasDriver = new LineDriverAdapter(previewDrawPanelController, CanvasFeature.getGuidesLineType(), "Canvas Preview");
        CoordinateTransformer scaleDown = new ScaleTransformer(0.5, 0.5);
        VisitableDriver previewDriver = new TransformingDriver(driver, scaleDown, "previewDriver");
        VisitableDriver previewCanvasDriver = new TransformingDriver(canvasDriver, scaleDown, "previewCanvasDriver");
        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getDriverCommandManager());
        CommandsHistoryWindow commandsHistoryWindow = new CommandsHistoryWindow(
                CommandsFeature.getCommandsHistory(),
                CommandsFeature.getDriverCommandManager()::setCurrentCommand
        );

        application.addWindowComponent("Command Manager", commandManager);
        application.addWindowComponent("Commands History Manager", commandsHistoryWindow);

        commandManager.initializePreviewPanel(previewDrawPanelController);
        
        CommandPreviewChangeObserver commandPreviewChangeObserver = new CommandPreviewChangeObserver(previewDrawPanelController, previewDriver, previewCanvasDriver, CommandsFeature.getDriverCommandManager());
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(commandPreviewChangeObserver);
        CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(commandManager);
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(windowObserver);
        CanvasFeature.getChangePublisher().addSubscriber(commandPreviewChangeObserver);
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
        new MouseClickToDriverCall(application.getFreePanel());
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Application app = new Application("Jobs 2D");

            // Przykład użycia automatycznego zarządzania funkcjami (features management)
            // Zarejestruj funkcje, które mają być automatycznie skonfigurowane
            FeaturesManager.registerFeature(new DrawerFeature());
            FeaturesManager.registerFeature(new CommandsFeature());
            FeaturesManager.registerFeature(new DriverFeature());
            FeaturesManager.registerFeature(new CanvasFeature());
            FeaturesManager.registerFeature(new ExtensionsFeature());
            FeaturesManager.registerFeature(new RecordingFeature());

            // Automatycznie skonfiguruj wszystkie zarejestrowane funkcje
            // To zastępuje ręczne wywołania setup dla każdej funkcji
            FeaturesManager.setupAllFeatures(app);

            setupDrivers(app);
            setupExtensions();
            setupPresetTests(app);
            setupCommandTests(app);
            setupLogger(app);
            setupWindows(app);
            setupMouseHandler(app);

            app.setVisibility(true);
        });
    }

}
