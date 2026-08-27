package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.DriverCommand;
import java.util.Stack;

public class EditorHistoryManager {

    private final Stack<DriverCommand> undoStack = new Stack<>();
    private final Stack<DriverCommand> redoStack = new Stack<>();

    public void saveState(DriverCommand currentState) {
        if (currentState != null) {
            undoStack.push(currentState.deepCopy());
            redoStack.clear();
        }
    }

    public DriverCommand undo(DriverCommand currentState) {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentState.deepCopy());
            return undoStack.pop();
        }
        return null;
    }

    public DriverCommand redo(DriverCommand currentState) {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentState.deepCopy());
            return redoStack.pop();
        }
        return null;
    }

    public void discardLastSave() {
        if (!undoStack.isEmpty()) {
            undoStack.pop();
        }
    }

    public void clearHistory() {
        undoStack.clear();
        redoStack.clear();
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}