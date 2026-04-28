package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

/**
 * Visitor interface for the DriverCommand hierarchy. Provides a separate
 * visit method for each concrete command type, enabling double-dispatch
 * traversal of command structures without modifying the command classes
 * themselves whenever a new operation is added.
 */
public interface ICommandVisitor {

    void visit(SetPositionCommand command);

    void visit(OperateToCommand command);

    void visit(ICompoundCommand command);

}