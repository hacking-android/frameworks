/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.LongPredicate;

public final class _$$Lambda$LongPredicate$_csV2YowuwbIk10M6jhbu2oq5to
implements LongPredicate {
    private final /* synthetic */ LongPredicate f$0;
    private final /* synthetic */ LongPredicate f$1;

    public /* synthetic */ _$$Lambda$LongPredicate$_csV2YowuwbIk10M6jhbu2oq5to(LongPredicate longPredicate, LongPredicate longPredicate2) {
        this.f$0 = longPredicate;
        this.f$1 = longPredicate2;
    }

    @Override
    public final boolean test(long l) {
        return LongPredicate.lambda$or$2(this.f$0, this.f$1, l);
    }
}

