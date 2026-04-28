package edu.kis.powp.jobs2d.command.visitor;

import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.drivers.transformations.CoordinateTransformer;

/**
 * Visitor that produces a transformed copy of a command hierarchy.
 * <p>
 * The original command tree is left untouched; for every leaf command
 * ({@link SetPositionCommand}, {@link OperateToCommand}) a new command
 * instance is created with coordinates computed by the provided
 * {@link CoordinateTransformer}. Compound commands are rebuilt
 * recursively so the resulting tree has the same shape as the input.
 * <p>
 * After a traversal, {@link #getTransformedCommand()} returns the
 * resulting command.
 *
 * <pre>
 * CoordinateTransformer t = new ScaleTransformer(2.0, 2.0);
 * CommandTransformVisitor v = new CommandTransformVisitor(t);
 * current.accept(v);
 * DriverCommand transformed = v.getTransformedCommand();
 * </pre>
 */
public class CommandTransformVisitor implements ICommandVisitor {

    private final CoordinateTransformer transformer;
    private DriverCommand result;

    /**
     * @param transformer transformer applied to every leaf command's
     *                    coordinates; must not be null.
     */
    public CommandTransformVisitor(CoordinateTransformer transformer) {
        if (transformer == null) {
            throw new IllegalArgumentException("transformer must not be null");
        }
        this.transformer = transformer;
    }

    @Override
    public void visit(SetPositionCommand command) {
        int[] newCoords = transformer.transform(command.getPosX(), command.getPosY());
        result = new SetPositionCommand(newCoords[0], newCoords[1]);
    }

    @Override
    public void visit(OperateToCommand command) {
        int[] newCoords = transformer.transform(command.getPosX(), command.getPosY());
        result = new OperateToCommand(newCoords[0], newCoords[1]);
    }

    @Override
    public void visit(ICompoundCommand command) {
        List<DriverCommand> transformedChildren = new ArrayList<>();
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
            if (result != null) {
                transformedChildren.add(result);
            }
        }
        CompoundCommand transformed = new CompoundCommand(transformedChildren,
                "Transformed(" + command.toString() + ")");
        result = transformed;
    }

    /**
     * @return the command produced by the last traversal, or {@code null}
     *         if the visitor has not visited any command yet.
     */
    public DriverCommand getTransformedCommand() {
        return result;
    }
}