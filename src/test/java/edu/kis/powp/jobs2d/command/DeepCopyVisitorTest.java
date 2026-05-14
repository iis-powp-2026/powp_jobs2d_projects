package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.visitor.DeepCopyVisitor;

import java.util.ArrayList;
import java.util.Arrays;

public class DeepCopyVisitorTest {

    public static void main(String[] args) {

        CompoundCommand innerOriginal = new CompoundCommand(
                new ArrayList<>(Arrays.asList(new SetPositionCommand(1, 1))), "inner");

        CompoundCommand outerOriginal = new CompoundCommand(
                new ArrayList<>(Arrays.asList(innerOriginal, new OperateToCommand(2, 2))), "outer");

        DeepCopyVisitor visitor = new DeepCopyVisitor();
        outerOriginal.accept(visitor);
        CompoundCommand outerCopy = (CompoundCommand) visitor.getDeepCopy();

        if (outerOriginal == outerCopy)
            throw new AssertionError("Outer compound must be a new object");

        if (outerOriginal.getCommandCount() != outerCopy.getCommandCount())
            throw new AssertionError("Copy must have the same number of commands");

        CompoundCommand innerCopy = (CompoundCommand) outerCopy.iterator().next();

        if (innerOriginal == innerCopy)
            throw new AssertionError("Inner compound must be a new object");

        DriverCommand innerChildOriginal = innerOriginal.iterator().next();
        DriverCommand innerChildCopy = innerCopy.iterator().next();

        if (innerChildOriginal == innerChildCopy)
            throw new AssertionError("Commands inside the inner nested compound must be new objects");

        innerOriginal.addCommand(new OperateToCommand(3, 3));

        if (innerCopy.getCommandCount() != 1) {
            throw new AssertionError("Nested copy was affected by changes to the original nested structure");
        }

        // Test with ImmutableCompoundCommand
        ImmutableCompoundCommand immutableOriginal = new ImmutableCompoundCommand("immutable",
                Arrays.asList(new SetPositionCommand(10, 10), new OperateToCommand(20, 20)));

        visitor = new DeepCopyVisitor();
        immutableOriginal.accept(visitor);
        CompoundCommand immutableCopy = (CompoundCommand) visitor.getDeepCopy();

        if (immutableOriginal.getCommandCount() != immutableCopy.getCommandCount())
            throw new AssertionError("Immutable copy must have the same number of commands");

        System.out.println("DEEP COPY VISITOR TEST PASSED");
    }
}