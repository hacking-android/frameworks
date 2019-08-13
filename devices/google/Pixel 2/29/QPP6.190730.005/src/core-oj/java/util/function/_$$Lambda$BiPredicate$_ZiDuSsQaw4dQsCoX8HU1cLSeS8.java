/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.BiPredicate;

public final class _$$Lambda$BiPredicate$_ZiDuSsQaw4dQsCoX8HU1cLSeS8
implements BiPredicate {
    private final /* synthetic */ BiPredicate f$0;

    public /* synthetic */ _$$Lambda$BiPredicate$_ZiDuSsQaw4dQsCoX8HU1cLSeS8(BiPredicate biPredicate) {
        this.f$0 = biPredicate;
    }

    public final boolean test(Object object, Object object2) {
        return BiPredicate.lambda$negate$1(this.f$0, object, object2);
    }
}

