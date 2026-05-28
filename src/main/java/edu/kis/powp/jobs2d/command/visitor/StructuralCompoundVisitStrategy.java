package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.ICompoundCommand;

/**
 * Structural mode keeps explicit compound boundaries.
 */
public class StructuralCompoundVisitStrategy implements CompoundVisitStrategy {

    @Override
    public void preCompoundVisit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor) {
        visitor.addCompoundStartBoundary();
    }

    @Override
    public void postCompoundVisit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor) {
        visitor.addCompoundEndBoundary();
    }
}

