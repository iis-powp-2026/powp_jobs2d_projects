package edu.kis.powp.jobs2d.command.comparator.comparison_strategy;

import edu.kis.powp.jobs2d.command.comparator.command_trace.CommandTrace;
import edu.kis.powp.jobs2d.command.comparator.command_trace.Line;

import java.util.List;

public class LineListComparator implements ComparisonStrategy {

    @Override
    public boolean compare(CommandTrace traceA, CommandTrace traceB) {
        List<Line> linesA =  traceA.getLines();
        List<Line> linesB = traceB.getLines();
        if(linesA.size() != linesB.size())
            return false;
        for (int i = 0; i < linesA.size(); i++) {
            if(!linesA.get(i).equals(linesB.get(i)))
                return false;
        }
        return true;
    }

}
