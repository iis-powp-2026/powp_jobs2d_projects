package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.LoggerCommandChangeObserver;
import edu.kis.powp.jobs2d.command.io.CommandImporterFactory;
import edu.kis.powp.jobs2d.command.io.JsonCommandImporterProvider;

public class CommandsFeature implements IFeature {

    private static CommandManager commandManager;

    @Override
    public void setup(Application application) {
        setupCommandManager();
    }

    @Override
    public String getName() {
        return "Commands";
    }

    public static void setupCommandManager() {
        commandManager = new CommandManager();

        LoggerCommandChangeObserver loggerObserver = new LoggerCommandChangeObserver();
        commandManager.getChangePublisher().addSubscriber(loggerObserver);

        CommandImporterFactory.registerProvider(new JsonCommandImporterProvider());
    }

    /**
     * Get manager of application driver command.
     * 
     * @return plotterCommandManager.
     */
    public static CommandManager getDriverCommandManager() {
        return commandManager;
    }
}
