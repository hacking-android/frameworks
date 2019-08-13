/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public final class _$$Lambda$BinaryOperator$V_WUclL0kAOZvMw9EtWtwAvmNJc
implements BinaryOperator {
    private final /* synthetic */ Comparator f$0;

    public /* synthetic */ _$$Lambda$BinaryOperator$V_WUclL0kAOZvMw9EtWtwAvmNJc(Comparator comparator) {
        this.f$0 = comparator;
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return BinaryOperator.lambda$maxBy$1(this.f$0, object, object2);
    }
}

