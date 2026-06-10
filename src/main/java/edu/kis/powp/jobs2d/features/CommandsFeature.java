package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.history.AddCurrentCommandToHistoryObserver;
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

    private static final int COMMANDS_HISTORY_MAX_SIZE = 35;

    public static void setupCommandsHistory()
    {
        commandsHistory = new CommandsHistory(COMMANDS_HISTORY_MAX_SIZE);

        AddCurrentCommandToHistoryObserver observer = new AddCurrentCommandToHistoryObserver(
                commandsHistory.getHistory(),
                commandsHistory.getMaxSize()
        );
        commandManager.getChangePublisher().addSubscriber(observer);
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
