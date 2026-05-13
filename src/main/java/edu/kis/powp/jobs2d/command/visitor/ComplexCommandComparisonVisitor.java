package edu.kis.powp.jobs2d.command.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;


public class ComplexCommandComparisonVisitor implements ICommandVisitor {

    private final List<String> signature = new ArrayList<>();

    @Override
    public void visit(SetPositionCommand command) {
        signature.add("SET:" + command.getPosX() + ":" + command.getPosY());
    }

    @Override
    public void visit(OperateToCommand command) {
        signature.add("OPERATE:" + command.getPosX() + ":" + command.getPosY());
    }

    @Override
    public void visit(ICompoundCommand command) {
        signature.add("COMPOUND_START");
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
        }
        signature.add("COMPOUND_END");
    }

    /**
     * Resets internal state so the same visitor can be reused.
     */
    public void reset() {
        signature.clear();
    }

    /**
     * @return immutable copy of the collected signature.
     */
    public List<String> getSignature() {
        return Collections.unmodifiableList(new ArrayList<>(signature));
    }

    /**
     * Compares two complex commands by structure, command type, order and coordinates.
     */
    public static boolean areEqual(ICompoundCommand left, ICompoundCommand right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }

        ComplexCommandComparisonVisitor leftVisitor = new ComplexCommandComparisonVisitor();
        ComplexCommandComparisonVisitor rightVisitor = new ComplexCommandComparisonVisitor();

        left.accept(leftVisitor);
        right.accept(rightVisitor);

        return leftVisitor.getSignature().equals(rightVisitor.getSignature());
    }
}

