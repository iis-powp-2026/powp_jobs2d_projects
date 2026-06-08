package edu.kis.powp.jobs2d.command.comparator.command_trace;

import java.util.List;

public class CommandTrace {
    private final List<Line> lines;

    public CommandTrace(List<Line> lines) {
        this.lines = lines;
    }

    public List<Line> getLines() {
        return List.copyOf(lines);
    }
}
