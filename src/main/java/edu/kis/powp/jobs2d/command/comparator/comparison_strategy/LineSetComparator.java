package edu.kis.powp.jobs2d.command.comparator.comparison_strategy;

import edu.kis.powp.jobs2d.command.comparator.command_trace.CommandTrace;
import edu.kis.powp.jobs2d.command.comparator.command_trace.Line;

import java.util.HashSet;

public class LineSetComparator implements ComparisonStrategy {
    @Override
    public boolean compare(CommandTrace traceA, CommandTrace traceB) {
        HashSet<Line> linesA =  new HashSet<>(traceA.getLines());
        HashSet<Line> linesB = new HashSet<>(traceB.getLines());

        if(linesA.size() != linesB.size())
            return false;

        return linesA.equals(linesB);
    }
}
