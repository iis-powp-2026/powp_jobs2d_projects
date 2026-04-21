package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.visitor.ICommandVisitor;

/**
 * DriverCommand interface.
 */
public interface DriverCommand {

    /**
     * Execute command on driver.
     *
     * @param driver driver.
     */
    public void execute(Job2dDriver driver);

    /**
     * Accept a visitor (Visitor pattern). Concrete command classes
     * dispatch to the matching visit method on the visitor.
     *
     * @param visitor the visitor to accept.
     */
    void accept(ICommandVisitor visitor);

    /**
     * Returns a deep copy of this command. Modifying the original or the copy
     * must not affect the other.
     *
     * @return independent deep copy of this command
     */
    DriverCommand deepCopy();

}