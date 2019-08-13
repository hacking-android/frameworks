/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.DoublePredicate;

public final class _$$Lambda$DoublePredicate$M8n9M3rXNLuHyZ2e0F7hUxAtVx0
implements DoublePredicate {
    private final /* synthetic */ DoublePredicate f$0;
    private final /* synthetic */ DoublePredicate f$1;

    public /* synthetic */ _$$Lambda$DoublePredicate$M8n9M3rXNLuHyZ2e0F7hUxAtVx0(DoublePredicate doublePredicate, DoublePredicate doublePredicate2) {
        this.f$0 = doublePredicate;
        this.f$1 = doublePredicate2;
    }

    @Override
    public final boolean test(double d) {
        return DoublePredicate.lambda$and$0(this.f$0, this.f$1, d);
    }
}

