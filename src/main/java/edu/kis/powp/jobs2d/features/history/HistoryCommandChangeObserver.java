package edu.kis.powp.jobs2d.features.history;

import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.observer.Subscriber;

public class HistoryCommandChangeObserver implements Subscriber {

    private HistoryManager historyManager;
    private CommandManager commandManager;

    public HistoryCommandChangeObserver(HistoryManager historyManager, CommandManager commandManager) {
        this.historyManager = historyManager;
        this.commandManager = commandManager;
    }

    @Override
    public void update() {
        if (commandManager.getCurrentCommand() != null) {
            historyManager.addHistory(new HistoryEntry("Command set: " + commandManager.getCurrentCommand().toString(), commandManager.getCurrentCommand()));
        }
    }

    public String toString() {
        return "History Command Change Observer";
    }
}
