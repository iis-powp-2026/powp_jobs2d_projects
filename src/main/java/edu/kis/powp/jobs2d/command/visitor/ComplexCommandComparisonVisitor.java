package edu.kis.powp.jobs2d.command.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;


public class ComplexCommandComparisonVisitor implements ICommandVisitor {

    private interface CompoundVisitStrategy {
        void visit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor);
    }

    public enum CompoundComparisonMode {
        STRUCTURAL {
            @Override
            CompoundVisitStrategy createStrategy() {
                return new StructuralCompoundVisitStrategy();
            }
        },
        FLATTEN {
            @Override
            CompoundVisitStrategy createStrategy() {
                return new FlattenCompoundVisitStrategy();
            }
        },
        IMPLEMENTATION_TYPE {
            @Override
            CompoundVisitStrategy createStrategy() {
                return new ImplementationTypeCompoundVisitStrategy();
            }
        };

        abstract CompoundVisitStrategy createStrategy();
    }

    private static final class StructuralCompoundVisitStrategy implements CompoundVisitStrategy {
        @Override
        public void visit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor) {
            visitor.addCompoundBoundary();
            visitor.visitChildren(command);
            visitor.addCompoundBoundary();
        }
    }

    private static final class FlattenCompoundVisitStrategy implements CompoundVisitStrategy {
        @Override
        public void visit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor) {
            visitor.visitChildren(command);
        }
    }

    private static final class ImplementationTypeCompoundVisitStrategy implements CompoundVisitStrategy {
        @Override
        public void visit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor) {
            visitor.signature.add("COMPOUND_TYPE:" + command.getClass().getSimpleName());
            visitor.addCompoundBoundary();
            visitor.visitChildren(command);
            visitor.addCompoundBoundary();
        }
    }

    private final List<String> signature = new ArrayList<>();
    private final CompoundVisitStrategy compoundVisitStrategy;

    public ComplexCommandComparisonVisitor() {
        this(CompoundComparisonMode.STRUCTURAL);
    }

    public ComplexCommandComparisonVisitor(CompoundComparisonMode mode) {
        CompoundComparisonMode selectedMode = mode == null ? CompoundComparisonMode.STRUCTURAL : mode;
        this.compoundVisitStrategy = selectedMode.createStrategy();
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
        compoundVisitStrategy.visit(command, this);
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

    private void addCompoundBoundary() {
        signature.add("COMPOUND_START");
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

