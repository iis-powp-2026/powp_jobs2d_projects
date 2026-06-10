package edu.kis.powp.jobs2d.command.comparator.command_trace;

import java.util.ArrayList;
import java.util.List;

public class CommandTraceBuilder {
    private final List<Line> lines = new ArrayList<>();
    private Point lastPos = new Point(0, 0);

    public CommandTraceBuilder() {}

    public CommandTraceBuilder setPos(int x, int y){
        lastPos = new Point(x, y);
        return this;
    }

    public CommandTraceBuilder operateTo(int x, int y){
        if (lastPos.getX() == x && lastPos.getY() == y){
            return this;
        }
        lines.add(new Line(lastPos,new Point(x, y)));
        lastPos = new Point(x, y);
        return this;
    }

    public CommandTrace build() {
        var ret = new CommandTrace(lines);
        lines.clear();
        return ret;
    }

}
