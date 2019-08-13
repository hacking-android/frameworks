/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.MatchOps;

public final class _$$Lambda$MatchOps$emK14UX33I4_nqH2o5l7hLEVAy8
implements Supplier {
    private final /* synthetic */ MatchOps.MatchKind f$0;
    private final /* synthetic */ IntPredicate f$1;

    public /* synthetic */ _$$Lambda$MatchOps$emK14UX33I4_nqH2o5l7hLEVAy8(MatchOps.MatchKind matchKind, IntPredicate intPredicate) {
        this.f$0 = matchKind;
        this.f$1 = intPredicate;
    }

    public final Object get() {
        return MatchOps.lambda$makeInt$1(this.f$0, this.f$1);
    }
}

