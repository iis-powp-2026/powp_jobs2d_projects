package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.*;

public class DeepCopyVisitor implements ICommandVisitor {

    private SimpleComplexCommandBuilder builder;

    public DeepCopyVisitor() {
        reset();
    }

    public void reset() {
        builder = new SimpleComplexCommandBuilder("Deep copy");
    }

    @Override
    public void visit(SetPositionCommand command) {
        builder.addCommand(new SetPositionCommand(command));
    }

    @Override
    public void visit(OperateToCommand command) {
        builder.addCommand(new OperateToCommand(command));
    }

    @Override
    public void visit(ICompoundCommand command) {
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
        }
    }

    @Override
    public String toString() {
        return "Deep copy visitor used.";
    }

    public ImmutableCompoundCommand getCommands() {
        return builder.buildImmutable();
    }
}
