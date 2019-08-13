/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.DoublePredicate;

public final class _$$Lambda$DoublePredicate$01E7YsTWsjaQSI72YV852C1Uqco
implements DoublePredicate {
    private final /* synthetic */ DoublePredicate f$0;

    public /* synthetic */ _$$Lambda$DoublePredicate$01E7YsTWsjaQSI72YV852C1Uqco(DoublePredicate doublePredicate) {
        this.f$0 = doublePredicate;
    }

    @Override
    public final boolean test(double d) {
        return DoublePredicate.lambda$negate$1(this.f$0, d);
    }
}

