package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.Arrays;

public class DeepCopyTest {

    public static void main(String[] args) {

        CompoundCommand innerOriginal = new CompoundCommand(
                new ArrayList<>(Arrays.asList(new SetPositionCommand(1, 1))), "inner");

        CompoundCommand outerOriginal = new CompoundCommand(
                new ArrayList<>(Arrays.asList(innerOriginal, new OperateToCommand(2, 2))), "outer");

        CompoundCommand outerCopy = (CompoundCommand) outerOriginal.deepCopy();

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

        System.out.println("TEST PASSED");
    }
}
