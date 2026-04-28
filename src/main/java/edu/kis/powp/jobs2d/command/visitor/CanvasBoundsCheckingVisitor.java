package edu.kis.powp.jobs2d.command.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

/**
 * Visitor that checks whether a command (typically a compound command)
 * exceeds the bounds of a given canvas. For each leaf command that lies
 * outside the canvas it records a separate entry, keeping per-type lists
 * so callers can distinguish between SetPosition and OperateTo violations.
 *
 * Call {@link #reset()} to reuse the same visitor instance for another
 * traversal.
 */
public class CanvasBoundsCheckingVisitor implements ICommandVisitor {

    private final ICanvas canvas;

    private final List<SetPositionCommand> exceedingSetPositionCommands = new ArrayList<>();
    private final List<OperateToCommand> exceedingOperateToCommands = new ArrayList<>();

    /**
     * @param canvas canvas defining the drawable area; must not be null.
     */
    public CanvasBoundsCheckingVisitor(ICanvas canvas) {
        if (canvas == null) {
            throw new IllegalArgumentException("canvas must not be null");
        }
        this.canvas = canvas;
    }

    @Override
    public void visit(SetPositionCommand command) {
        if (!canvas.contains(command.getPosX(), command.getPosY())) {
            exceedingSetPositionCommands.add(command);
        }
    }

    @Override
    public void visit(OperateToCommand command) {
        if (!canvas.contains(command.getPosX(), command.getPosY())) {
            exceedingOperateToCommands.add(command);
        }
    }

    @Override
    public void visit(ICompoundCommand command) {
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
        }
    }

    /**
     * Reset all collected results so the visitor instance can be reused.
     */
    public void reset() {
        exceedingSetPositionCommands.clear();
        exceedingOperateToCommands.clear();
    }

    public ICanvas getCanvas() {
        return canvas;
    }

    public boolean hasExceeded() {
        return !exceedingSetPositionCommands.isEmpty() || !exceedingOperateToCommands.isEmpty();
    }

    public int getExceedingSetPositionCount() {
        return exceedingSetPositionCommands.size();
    }

    public int getExceedingOperateToCount() {
        return exceedingOperateToCommands.size();
    }

    public int getTotalExceedingCount() {
        return exceedingSetPositionCommands.size() + exceedingOperateToCommands.size();
    }

    public List<SetPositionCommand> getExceedingSetPositionCommands() {
        return Collections.unmodifiableList(exceedingSetPositionCommands);
    }

    public List<OperateToCommand> getExceedingOperateToCommands() {
        return Collections.unmodifiableList(exceedingOperateToCommands);
    }
}