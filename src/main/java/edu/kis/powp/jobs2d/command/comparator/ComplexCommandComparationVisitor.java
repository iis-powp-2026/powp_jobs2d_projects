package edu.kis.powp.jobs2d.command.comparator;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.comparator.command_trace.CommandTrace;
import edu.kis.powp.jobs2d.command.comparator.command_trace.CommandTraceBuilder;
import edu.kis.powp.jobs2d.command.visitor.ICommandVisitor;

public class ComplexCommandComparationVisitor implements ICommandVisitor {
    private final CommandTraceBuilder commandTraceBuilder = new CommandTraceBuilder();

    @Override
    public void visit(SetPositionCommand command) {
        commandTraceBuilder.setPos(command.getPosX(), command.getPosY());
    }

    @Override
    public void visit(OperateToCommand command) {
        commandTraceBuilder.operateTo(command.getPosX(), command.getPosY());
    }

    @Override
    public void visit(ICompoundCommand command) {
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
        }
    }

    public CommandTrace getCommandTrace() {
        return commandTraceBuilder.build();
    }
}
