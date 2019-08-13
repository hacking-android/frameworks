/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.IntPredicate;

public final class _$$Lambda$IntPredicate$K711jAs3Mu_dbXoV61T3AbYlIaU
implements IntPredicate {
    private final /* synthetic */ IntPredicate f$0;
    private final /* synthetic */ IntPredicate f$1;

    public /* synthetic */ _$$Lambda$IntPredicate$K711jAs3Mu_dbXoV61T3AbYlIaU(IntPredicate intPredicate, IntPredicate intPredicate2) {
        this.f$0 = intPredicate;
        this.f$1 = intPredicate2;
    }

    @Override
    public final boolean test(int n) {
        return IntPredicate.lambda$or$2(this.f$0, this.f$1, n);
    }
}

