package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.history.CommandsHistory;
import edu.kis.powp.jobs2d.command.io.CommandImporterFactory;
import edu.kis.powp.jobs2d.command.io.JsonCommandImporterProvider;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.LoggerCommandChangeObserver;

public class CommandsFeature implements IFeature {

    private static CommandManager commandManager;
    private static CommandsHistory commandsHistory;

    @Override
    public void setup(Application application) {
        setupCommandManager();
        setupCommandsHistory();
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

    public static void setupCommandsHistory()
    {
        commandsHistory = new CommandsHistory(35);
    }

    /**
     * Get manager of application driver command.
     * 
     * @return plotterCommandManager.
     */
    public static CommandManager getDriverCommandManager() {
        return commandManager;
    }

    public static CommandsHistory getCommandsHistory()
    {
        return commandsHistory;
    }
}
