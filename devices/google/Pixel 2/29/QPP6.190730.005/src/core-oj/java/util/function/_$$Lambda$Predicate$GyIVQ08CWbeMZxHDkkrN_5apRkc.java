/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.Predicate;

public final class _$$Lambda$Predicate$GyIVQ08CWbeMZxHDkkrN_5apRkc
implements Predicate {
    private final /* synthetic */ Predicate f$0;
    private final /* synthetic */ Predicate f$1;

    public /* synthetic */ _$$Lambda$Predicate$GyIVQ08CWbeMZxHDkkrN_5apRkc(Predicate predicate, Predicate predicate2) {
        this.f$0 = predicate;
        this.f$1 = predicate2;
    }

    public final boolean test(Object object) {
        return Predicate.lambda$and$0(this.f$0, this.f$1, object);
    }
}

