package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.visitor.ComplexCommandComparisonVisitor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import edu.kis.powp.jobs2d.command.visitor.ComplexCommandComparisonVisitor.CompoundComparisonMode;

public class ComplexCommandComparisonVisitorTest {

    @Test
    void shouldCompareEqualNestedCommands() {
        ICompoundCommand left = buildNestedSquare();
        ICompoundCommand right = buildNestedSquare();

        assertTrue(ComplexCommandComparisonVisitor.areEqual(left, right),
                "Equivalent nested commands should be equal");
    }

    @Test
    void shouldDetectDifferentCoordinates() {
        ICompoundCommand left = buildNestedSquare();

        CompoundCommand inner = new CompoundCommand();
        inner.addCommand(new SetPositionCommand(10, 10));
        inner.addCommand(new OperateToCommand(21, 10));

        CompoundCommand right = new CompoundCommand();
        right.addCommand(inner);
        right.addCommand(new OperateToCommand(20, 20));

        assertFalse(ComplexCommandComparisonVisitor.areEqual(left, right),
                "Commands with different coordinates should not be equal");
    }

    @Test
    void shouldDetectDifferentOrder() {
        CompoundCommand left = new CompoundCommand();
        left.addCommand(new SetPositionCommand(0, 0));
        left.addCommand(new OperateToCommand(10, 0));

        CompoundCommand right = new CompoundCommand();
        right.addCommand(new OperateToCommand(10, 0));
        right.addCommand(new SetPositionCommand(0, 0));

        assertFalse(ComplexCommandComparisonVisitor.areEqual(left, right),
                "Commands with different order should not be equal");
    }

    @Test
    void shouldDetectDifferentNesting() {
        CompoundCommand flat = new CompoundCommand();
        flat.addCommand(new SetPositionCommand(1, 1));
        flat.addCommand(new OperateToCommand(2, 2));

        CompoundCommand nested = new CompoundCommand();
        CompoundCommand child = new CompoundCommand();
        child.addCommand(new SetPositionCommand(1, 1));
        nested.addCommand(child);
        nested.addCommand(new OperateToCommand(2, 2));

        assertFalse(ComplexCommandComparisonVisitor.areEqual(flat, nested),
                "Commands with different nesting should not be equal");
    }

    @Test
    void flattenModeIgnoresNesting() {
        CompoundCommand nested = (CompoundCommand) buildNestedSquare();

        CompoundCommand flat = new CompoundCommand();
        flat.addCommand(new SetPositionCommand(10, 10));
        flat.addCommand(new OperateToCommand(20, 10));
        flat.addCommand(new OperateToCommand(20, 20));

        assertTrue(ComplexCommandComparisonVisitor.areEqual(nested, flat, CompoundComparisonMode.FLATTEN),
                "Flatten mode should consider nested and flattened sequences equivalent");
    }

    @Test
    void implementationTypeModeDetectsDifferentConcreteTypes() {
        CompoundCommand mutable = new CompoundCommand(
                new ArrayList<>(Arrays.asList(new SetPositionCommand(10, 10), new OperateToCommand(20, 20))),
                "mutable");

        ImmutableCompoundCommand immutable = new ImmutableCompoundCommand(
                "immutable",
                new ArrayList<>(Arrays.asList(new SetPositionCommand(10, 10), new OperateToCommand(20, 20))));

        assertFalse(ComplexCommandComparisonVisitor.areEqual(mutable, immutable, CompoundComparisonMode.IMPLEMENTATION_TYPE),
                "Implementation-type mode should detect different concrete compound implementations");
    }

    @Test
    void shouldCompareCompoundAndImmutableAsEqualWhenEquivalent() {
        CompoundCommand mutable = new CompoundCommand(
                new ArrayList<>(Arrays.asList(new SetPositionCommand(10, 10), new OperateToCommand(20, 20))),
                "mutable");

        ImmutableCompoundCommand immutable = new ImmutableCompoundCommand(
                "immutable",
                new ArrayList<>(Arrays.asList(new SetPositionCommand(10, 10), new OperateToCommand(20, 20))));

        assertTrue(ComplexCommandComparisonVisitor.areEqual(mutable, immutable),
                "Equivalent mutable and immutable compounds should be equal");
    }

    private ICompoundCommand buildNestedSquare() {
        CompoundCommand inner = new CompoundCommand();
        inner.addCommand(new SetPositionCommand(10, 10));
        inner.addCommand(new OperateToCommand(20, 10));

        CompoundCommand outer = new CompoundCommand();
        outer.addCommand(inner);
        outer.addCommand(new OperateToCommand(20, 20));
        return outer;
    }
}


