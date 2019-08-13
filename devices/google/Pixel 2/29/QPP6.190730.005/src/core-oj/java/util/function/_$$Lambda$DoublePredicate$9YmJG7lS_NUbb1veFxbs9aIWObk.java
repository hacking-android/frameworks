/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.DoublePredicate;

public final class _$$Lambda$DoublePredicate$9YmJG7lS_NUbb1veFxbs9aIWObk
implements DoublePredicate {
    private final /* synthetic */ DoublePredicate f$0;
    private final /* synthetic */ DoublePredicate f$1;

    public /* synthetic */ _$$Lambda$DoublePredicate$9YmJG7lS_NUbb1veFxbs9aIWObk(DoublePredicate doublePredicate, DoublePredicate doublePredicate2) {
        this.f$0 = doublePredicate;
        this.f$1 = doublePredicate2;
    }

    @Override
    public final boolean test(double d) {
        return DoublePredicate.lambda$or$2(this.f$0, this.f$1, d);
    }
}

