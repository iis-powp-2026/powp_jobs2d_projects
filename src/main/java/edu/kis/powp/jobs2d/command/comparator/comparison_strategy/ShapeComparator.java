package edu.kis.powp.jobs2d.command.comparator.comparison_strategy;

import edu.kis.powp.jobs2d.command.comparator.command_trace.CommandTrace;
import edu.kis.powp.jobs2d.command.comparator.command_trace.Line;
import edu.kis.powp.jobs2d.command.comparator.command_trace.Point;

import java.util.HashSet;
import java.util.List;

public class ShapeComparator implements ComparisonStrategy {
    @Override
    public boolean compare(CommandTrace traceA, CommandTrace traceB) {
        List<Line> linesA =  traceA.getLines();
        List<Line> linesB = traceB.getLines();
        if(linesA.size() != linesB.size())
            return false;

        int maxXChangeA = getMaxDifferenceX(linesA);
        int maxXChangeB = getMaxDifferenceX(linesB);

        if (maxXChangeA == maxXChangeB) {
            HashSet<Line> setA = new HashSet<>(linesA);
            HashSet<Line> setB = new HashSet<>(linesB);
            return setA.equals(setB);
        }else{
            HashSet<Line> setA = new HashSet<>();
            HashSet<Line> setB = new HashSet<>();
            for(Line lineA : linesA){
                setA.add(scaleLine(lineA, maxXChangeB));
            }
            for(Line lineB : linesB){
                setB.add(scaleLine(lineB, maxXChangeA));
            }
            return setA.equals(setB);
        }

    }

    private int getMaxDifferenceX(List<Line> lines) {
        int maxDifference = 0;
        for(Line line: lines) {
            int difference = Math.abs(line.getStart().getX() - line.getEnd().getX());
            if(difference > maxDifference)
                maxDifference = difference;
        }
        return maxDifference;
    }

    private Line scaleLine(Line line, int scale) {
        Point scaledStart = new Point(line.getStart().getX() * scale, line.getStart().getY() * scale);
        Point scaledEnd = new Point(line.getEnd().getX() * scale, line.getEnd().getY() * scale);
        return new Line(scaledStart, scaledEnd);
    }
}
