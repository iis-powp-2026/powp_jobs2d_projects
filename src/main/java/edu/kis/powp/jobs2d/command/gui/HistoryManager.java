package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.DriverCommand;

public interface HistoryManager {
    void saveState(DriverCommand currentState);
    DriverCommand undo(DriverCommand currentState);
    DriverCommand redo(DriverCommand currentState);
    void discardLastSave();
    void clearHistory();
    boolean canUndo();
    boolean canRedo();
}