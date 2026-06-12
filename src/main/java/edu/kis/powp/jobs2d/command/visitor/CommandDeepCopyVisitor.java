package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.SimpleComplexCommandBuilder;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

public class CommandDeepCopyVisitor implements ICommandVisitor {

    private DriverCommand copy;

    @Override
    public void visit(SetPositionCommand command) {
        copy = new SetPositionCommand(command.getPosX(), command.getPosY());
    }

    @Override
    public void visit(OperateToCommand command) {
        copy = new OperateToCommand(command.getPosX(), command.getPosY());
    }

    @Override
    public void visit(ICompoundCommand command) {
        SimpleComplexCommandBuilder builder = new SimpleComplexCommandBuilder(command.toString());
        buildChildren(builder, command);
        copy = builder.buildImmutable();
    }

    @Override
    public void visit(CompoundCommand command) {
        SimpleComplexCommandBuilder builder = new SimpleComplexCommandBuilder(command.getName());
        buildChildren(builder, command);
        copy = builder.build();
    }

    @Override
    public void visit(ImmutableCompoundCommand command) {
        SimpleComplexCommandBuilder builder = new SimpleComplexCommandBuilder(command.getName());
        buildChildren(builder, command);
        copy = builder.buildImmutable();
    }

    private void buildChildren(SimpleComplexCommandBuilder builder, ICompoundCommand command) {
        for (DriverCommand child : command) {
            child.accept(this);
            if (copy != null) {
                builder.addCommand(copy);
            }
        }
    }

    public DriverCommand getCopy() {
        return copy;
    }
}
