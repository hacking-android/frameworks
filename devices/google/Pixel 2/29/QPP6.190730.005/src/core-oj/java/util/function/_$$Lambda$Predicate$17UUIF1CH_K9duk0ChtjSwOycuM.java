/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.Predicate;

public final class _$$Lambda$Predicate$17UUIF1CH_K9duk0ChtjSwOycuM
implements Predicate {
    private final /* synthetic */ Predicate f$0;
    private final /* synthetic */ Predicate f$1;

    public /* synthetic */ _$$Lambda$Predicate$17UUIF1CH_K9duk0ChtjSwOycuM(Predicate predicate, Predicate predicate2) {
        this.f$0 = predicate;
        this.f$1 = predicate2;
    }

    public final boolean test(Object object) {
        return Predicate.lambda$or$2(this.f$0, this.f$1, object);
    }
}

