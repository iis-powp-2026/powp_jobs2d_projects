package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.ICompoundCommand;

/**
 * Implementation-type mode includes the concrete compound class name in the signature.
 */
public class ImplementationTypeCompoundVisitStrategy implements CompoundVisitStrategy {

    @Override
    public void preCompoundVisit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor) {
        visitor.addCompoundTypeBoundary(command);
        visitor.addCompoundStartBoundary();
    }

    @Override
    public void postCompoundVisit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor) {
        visitor.addCompoundEndBoundary();
    }
}

