package edu.kis.powp.jobs2d.command.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

public class ComplexCommandComparisonVisitor implements ICommandVisitor {

    public enum CompoundComparisonMode {
        STRUCTURAL,
        FLATTEN,
        IMPLEMENTATION_TYPE
    }

    private final List<String> signature = new ArrayList<>();
    private final CompoundVisitStrategy compoundVisitStrategy;

    public ComplexCommandComparisonVisitor() {
        this(CompoundComparisonMode.STRUCTURAL);
    }

    public ComplexCommandComparisonVisitor(CompoundComparisonMode mode) {
        this(CompoundVisitStrategyFactory.create(mode));
    }

    public ComplexCommandComparisonVisitor(CompoundVisitStrategy compoundVisitStrategy) {
        this.compoundVisitStrategy = compoundVisitStrategy == null
                ? CompoundVisitStrategyFactory.create(CompoundComparisonMode.STRUCTURAL)
                : compoundVisitStrategy;
    }

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
        compoundVisitStrategy.preCompoundVisit(command, this);
        visitChildren(command);
        compoundVisitStrategy.postCompoundVisit(command, this);
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
     * Public helper for strategies that want to emit a compound start boundary.
     */
    public void addCompoundStartBoundary() {
        signature.add("COMPOUND_START");
    }

    /**
     * Public helper for strategies that want to emit a compound end boundary.
     */
    public void addCompoundEndBoundary() {
        signature.add("COMPOUND_END");
    }

    /**
     * Public helper for strategies that want to emit a compound implementation marker.
     */
    public void addCompoundTypeBoundary(ICompoundCommand command) {
        signature.add("COMPOUND_TYPE:" + command.getClass().getSimpleName());
    }

    private void visitChildren(ICompoundCommand command) {
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
        }
    }

    /**
     * Compares two complex commands by structure, command type, order and coordinates using the default
     * compound-handling mode (STRUCTURAL).
     */
    public static boolean areEqual(ICompoundCommand left, ICompoundCommand right) {
        return areEqual(left, right, CompoundComparisonMode.STRUCTURAL);
    }

    /**
     * Compares two complex commands by structure, command type, order and coordinates using the provided
     * compound-handling mode.
     */
    public static boolean areEqual(ICompoundCommand left, ICompoundCommand right, CompoundComparisonMode mode) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }

        ComplexCommandComparisonVisitor leftVisitor = new ComplexCommandComparisonVisitor(mode);
        ComplexCommandComparisonVisitor rightVisitor = new ComplexCommandComparisonVisitor(mode);

        left.accept(leftVisitor);
        right.accept(rightVisitor);

        return leftVisitor.getSignature().equals(rightVisitor.getSignature());
    }
}
