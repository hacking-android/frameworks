/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.LongPredicate;
import java.util.function.Supplier;
import java.util.stream.MatchOps;

public final class _$$Lambda$MatchOps$kCrOdGmndcbZklBaJ6Z4blQ1F5M
implements Supplier {
    private final /* synthetic */ MatchOps.MatchKind f$0;
    private final /* synthetic */ LongPredicate f$1;

    public /* synthetic */ _$$Lambda$MatchOps$kCrOdGmndcbZklBaJ6Z4blQ1F5M(MatchOps.MatchKind matchKind, LongPredicate longPredicate) {
        this.f$0 = matchKind;
        this.f$1 = longPredicate;
    }

    public final Object get() {
        return MatchOps.lambda$makeLong$2(this.f$0, this.f$1);
    }
}

