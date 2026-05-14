package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.SimpleComplexCommandBuilder;

/**
 * Visitor that produces a deep copy of a command hierarchy.
 * <p>
 * The original command tree is left untouched; for every command
 * a new command instance is created with the same coordinates and structure.
 * Compound commands are rebuilt recursively so the resulting tree has
 * the same shape as the input.
 * <p>
 * After a traversal, {@link #getDeepCopy()} returns the resulting command.
 *
 * <pre>
 * DeepCopyVisitor v = new DeepCopyVisitor();
 * originalCommand.accept(v);
 * DriverCommand copy = v.getDeepCopy();
 * </pre>
 */
public class DeepCopyVisitor implements ICommandVisitor {

    private DriverCommand result;

    @Override
    public void visit(SetPositionCommand command) {
        result = new SetPositionCommand(command.getPosX(), command.getPosY());
    }

    @Override
    public void visit(OperateToCommand command) {
        result = new OperateToCommand(command.getPosX(), command.getPosY());
    }

    @Override
    public void visit(ICompoundCommand command) {
        SimpleComplexCommandBuilder builder = new SimpleComplexCommandBuilder();
        builder.name("DeepCopy(" + command.toString() + ")");
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
            if (result != null) {
                builder.addCommand(result);
            }
        }
        result = builder.build();
    }

    /**
     * @return the command produced by the last traversal, or {@code null}
     *         if the visitor has not visited any command yet.
     */
    public DriverCommand getDeepCopy() {
        return result;
    }
}