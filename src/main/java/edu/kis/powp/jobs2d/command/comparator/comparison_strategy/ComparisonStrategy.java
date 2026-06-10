package edu.kis.powp.jobs2d.command.comparator.comparison_strategy;

import edu.kis.powp.jobs2d.command.comparator.command_trace.CommandTrace;

public interface ComparisonStrategy {
    boolean compare(CommandTrace traceA, CommandTrace traceB);
}
