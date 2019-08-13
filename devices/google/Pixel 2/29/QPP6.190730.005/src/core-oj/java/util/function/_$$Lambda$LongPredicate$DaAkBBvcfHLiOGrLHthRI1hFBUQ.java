/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.LongPredicate;

public final class _$$Lambda$LongPredicate$DaAkBBvcfHLiOGrLHthRI1hFBUQ
implements LongPredicate {
    private final /* synthetic */ LongPredicate f$0;
    private final /* synthetic */ LongPredicate f$1;

    public /* synthetic */ _$$Lambda$LongPredicate$DaAkBBvcfHLiOGrLHthRI1hFBUQ(LongPredicate longPredicate, LongPredicate longPredicate2) {
        this.f$0 = longPredicate;
        this.f$1 = longPredicate2;
    }

    @Override
    public final boolean test(long l) {
        return LongPredicate.lambda$and$0(this.f$0, this.f$1, l);
    }
}

