package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.ICompoundCommand;

/**
 * Strategy describing how compound commands should be represented in a comparison signature.
 */
public interface CompoundVisitStrategy {

    void preCompoundVisit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor);

    void postCompoundVisit(ICompoundCommand command, ComplexCommandComparisonVisitor visitor);
}

