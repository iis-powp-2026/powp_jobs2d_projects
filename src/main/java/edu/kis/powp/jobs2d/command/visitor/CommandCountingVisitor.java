package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

/**
 * Visitor that counts commands in a command hierarchy, keeping separate
 * counts per command type. Call {@link #reset()} to reuse the same
 * visitor instance for another traversal.
 */
public class CommandCountingVisitor implements ICommandVisitor {

    private int setPositionCount = 0;
    private int operateToCount = 0;
    private int compoundCount = 0;

    @Override
    public void visit(SetPositionCommand command) {
        setPositionCount++;
    }

    @Override
    public void visit(OperateToCommand command) {
        operateToCount++;
    }

    @Override
    public void visit(ICompoundCommand command) {
        compoundCount++;
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
        }
    }

    /**
     * Reset all counters so the visitor instance can be reused.
     */
    public void reset() {
        setPositionCount = 0;
        operateToCount = 0;
        compoundCount = 0;
    }

    public int getSetPositionCount() {
        return setPositionCount;
    }

    public int getOperateToCount() {
        return operateToCount;
    }

    public int getCompoundCount() {
        return compoundCount;
    }

    /**
     * @return total number of leaf commands (SetPosition + OperateTo).
     */
    public int getLeafCount() {
        return setPositionCount + operateToCount;
    }

    /**
     * @return total number of commands visited (leaves + compound).
     */
    public int getTotalCount() {
        return setPositionCount + operateToCount + compoundCount;
    }

    @Override
    public String toString() {
        return "CommandCountingVisitor{"
                + "setPosition=" + setPositionCount
                + ", operateTo=" + operateToCount
                + ", compound=" + compoundCount
                + ", leaves=" + getLeafCount()
                + ", total=" + getTotalCount()
                + '}';
    }
}