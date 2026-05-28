package edu.kis.powp.jobs2d.command.visitor;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Factory for compound-visit strategies used by the comparison visitor.
 * Supports registry-based strategy creation to stay open for extension.
 */
public final class CompoundVisitStrategyFactory {

    private static final Map<ComplexCommandComparisonVisitor.CompoundComparisonMode, Supplier<CompoundVisitStrategy>> REGISTRY =
            new EnumMap<>(ComplexCommandComparisonVisitor.CompoundComparisonMode.class);

    static {
        register(ComplexCommandComparisonVisitor.CompoundComparisonMode.STRUCTURAL, StructuralCompoundVisitStrategy::new);
        register(ComplexCommandComparisonVisitor.CompoundComparisonMode.FLATTEN, FlattenCompoundVisitStrategy::new);
        register(ComplexCommandComparisonVisitor.CompoundComparisonMode.IMPLEMENTATION_TYPE,
                ImplementationTypeCompoundVisitStrategy::new);
    }

    private CompoundVisitStrategyFactory() {
    }

    public static void register(ComplexCommandComparisonVisitor.CompoundComparisonMode mode,
                                Supplier<CompoundVisitStrategy> supplier) {
        if (mode == null || supplier == null) {
            return;
        }
        REGISTRY.put(mode, supplier);
    }

    public static CompoundVisitStrategy create(ComplexCommandComparisonVisitor.CompoundComparisonMode mode) {
        ComplexCommandComparisonVisitor.CompoundComparisonMode selectedMode =
                mode == null ? ComplexCommandComparisonVisitor.CompoundComparisonMode.STRUCTURAL : mode;

        Supplier<CompoundVisitStrategy> supplier = REGISTRY.get(selectedMode);
        if (supplier != null) {
            return supplier.get();
        }
        return new StructuralCompoundVisitStrategy();
    }
}
