package edu.kis.powp.jobs2d.command.comparator.comparison_strategy;

import edu.kis.powp.jobs2d.command.comparator.command_trace.CommandTrace;
import edu.kis.powp.jobs2d.command.comparator.command_trace.Line;

import java.util.List;

public class LineListComparator implements ComparisonStrategy {

    @Override
    public boolean compare(CommandTrace traceA, CommandTrace traceB) {
        List<Line> linesA =  traceA.getLines();
        List<Line> linesB = traceB.getLines();
        return linesA.equals(linesB);
    }

}
