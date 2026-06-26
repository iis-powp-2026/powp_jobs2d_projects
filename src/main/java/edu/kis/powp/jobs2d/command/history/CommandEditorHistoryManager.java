package edu.kis.powp.jobs2d.command.history;

import edu.kis.powp.jobs2d.command.SimpleComplexCommandBuilder;
import java.util.Stack;

public class CommandEditorHistoryManager {

    private final SimpleComplexCommandBuilder builder;
    private final Stack<BuilderMemento> undoStack;
    private final Stack<BuilderMemento> redoStack;

    public CommandEditorHistoryManager(SimpleComplexCommandBuilder builder) {
        this.builder = builder;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void saveState() {
        undoStack.push(builder.saveToMemento());
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(builder.saveToMemento());
            BuilderMemento previousState = undoStack.pop();
            builder.restoreFromMemento(previousState);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(builder.saveToMemento());
            BuilderMemento nextState = redoStack.pop();
            builder.restoreFromMemento(nextState);
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}