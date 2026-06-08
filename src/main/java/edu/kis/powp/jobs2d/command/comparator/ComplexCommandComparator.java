package edu.kis.powp.jobs2d.command.comparator;

import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.comparator.comparison_strategy.ComparisonStrategy;

public class ComplexCommandComparator {
    private final ComparisonStrategy comparisonStrategy;

    public ComplexCommandComparator(ComparisonStrategy comparisonStrategy){
        this.comparisonStrategy = comparisonStrategy;

    }

    public boolean compare(ICompoundCommand command1, ICompoundCommand command2) {
        ComplexCommandComparationVisitor visitor1 = new ComplexCommandComparationVisitor();
        ComplexCommandComparationVisitor visitor2 = new ComplexCommandComparationVisitor();

        command1.accept(visitor1);
        command2.accept(visitor2);

        return comparisonStrategy.compare(visitor1.getCommandTrace(),visitor2.getCommandTrace());
    }
}
