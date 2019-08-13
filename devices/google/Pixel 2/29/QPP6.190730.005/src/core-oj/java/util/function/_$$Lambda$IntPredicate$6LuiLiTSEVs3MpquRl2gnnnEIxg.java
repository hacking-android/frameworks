/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.IntPredicate;

public final class _$$Lambda$IntPredicate$6LuiLiTSEVs3MpquRl2gnnnEIxg
implements IntPredicate {
    private final /* synthetic */ IntPredicate f$0;

    public /* synthetic */ _$$Lambda$IntPredicate$6LuiLiTSEVs3MpquRl2gnnnEIxg(IntPredicate intPredicate) {
        this.f$0 = intPredicate;
    }

    @Override
    public final boolean test(int n) {
        return IntPredicate.lambda$negate$1(this.f$0, n);
    }
}

