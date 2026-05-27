package edu.kis.powp.jobs2d.command;

import java.util.HashSet;
import java.util.Set;

import edu.kis.powp.jobs2d.command.visitor.ICommandVisitor;

public class ComplexCommandComparationVisitor implements ICommandVisitor {
    
    private class Point
    {
        private int x;
        private int y;
    
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    
        public int getX() {
            return x;
        }
    
        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }
    }

    private class Line
    {
        private Point start;
        private Point end;
    
        public Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }
    
        public Point getStart() {
            return start;
        }
    
        public Point getEnd() {
            return end;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Line line = (Line) obj;
            return start.equals(line.start) && end.equals(line.end);
        }
    }

    private Point lastPos = null;
    private Set<Line> lines = new HashSet<>();

    @Override
    public void visit(SetPositionCommand command) {
        lastPos = new Point(command.getPosX(), command.getPosY());
    }

    @Override
    public void visit(OperateToCommand command) {
        if (lastPos == null) {
            lastPos = new Point(0, 0);
        }
        Point curPos = new Point(command.getPosX(), command.getPosY());
        Line line = new Line(lastPos, curPos);
        lastPos = line.getEnd();
        lines.add(line);
    }

    @Override
    public void visit(ICompoundCommand command) {
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
        }
    }
    
    public void reset()
    {
        lastPos = null;
        lines.clear();
    }

    public Set<Line> getLines() {
        return lines;
    }

    public static boolean compare(ICompoundCommand command1, ICompoundCommand command2) {
        ComplexCommandComparationVisitor visitor1 = new ComplexCommandComparationVisitor();
        ComplexCommandComparationVisitor visitor2 = new ComplexCommandComparationVisitor();

        command1.accept(visitor1);
        command2.accept(visitor2);

        return visitor1.getLines().equals(visitor2.getLines());
    }
}
