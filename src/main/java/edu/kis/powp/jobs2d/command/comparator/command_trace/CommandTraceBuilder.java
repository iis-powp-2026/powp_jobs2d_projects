package edu.kis.powp.jobs2d.command.comparator.command_trace;

import java.util.ArrayList;
import java.util.List;

public class CommandTraceBuilder {
    private final List<Line> lines = new ArrayList<>();
    private Point lastPos = new Point(0, 0);

    public CommandTraceBuilder() {}

    public void setPos(int x, int y){
        lastPos = new Point(x, y);
    }

    public void operateTo(int x, int y){
        if (lastPos.getX() == x && lastPos.getY() == y){
            return;
        }
        lines.add(new Line(lastPos,new Point(x, y)));
        lastPos = new Point(x, y);
    }

    public CommandTrace build() {
        return new CommandTrace(lines);
    }

}
