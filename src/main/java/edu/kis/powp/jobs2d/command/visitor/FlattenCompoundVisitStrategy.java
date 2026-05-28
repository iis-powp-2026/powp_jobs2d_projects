package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.ICompoundCommand;

/**
 * Flatten mode ignores compound nesting and treats children as a single linear chain.
 */
public class FlattenCompoundVisitStrategy implements CompoundVisitStrategy {

    @Override
    public void preCompoundVisit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor) {
        // no-op
    }

    @Override
    public void postCompoundVisit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor) {
        // no-op
    }
}

