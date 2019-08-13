/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.BiPredicate;

public final class _$$Lambda$BiPredicate$OpXxJPTnCjvZwHcfaL3bBDqxCyQ
implements BiPredicate {
    private final /* synthetic */ BiPredicate f$0;
    private final /* synthetic */ BiPredicate f$1;

    public /* synthetic */ _$$Lambda$BiPredicate$OpXxJPTnCjvZwHcfaL3bBDqxCyQ(BiPredicate biPredicate, BiPredicate biPredicate2) {
        this.f$0 = biPredicate;
        this.f$1 = biPredicate2;
    }

    public final boolean test(Object object, Object object2) {
        return BiPredicate.lambda$or$2(this.f$0, this.f$1, object, object2);
    }
}

