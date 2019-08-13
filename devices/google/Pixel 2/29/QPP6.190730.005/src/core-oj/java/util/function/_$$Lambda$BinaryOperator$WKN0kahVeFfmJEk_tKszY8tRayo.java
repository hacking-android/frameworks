/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public final class _$$Lambda$BinaryOperator$WKN0kahVeFfmJEk_tKszY8tRayo
implements BinaryOperator {
    private final /* synthetic */ Comparator f$0;

    public /* synthetic */ _$$Lambda$BinaryOperator$WKN0kahVeFfmJEk_tKszY8tRayo(Comparator comparator) {
        this.f$0 = comparator;
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return BinaryOperator.lambda$minBy$0(this.f$0, object, object2);
    }
}

