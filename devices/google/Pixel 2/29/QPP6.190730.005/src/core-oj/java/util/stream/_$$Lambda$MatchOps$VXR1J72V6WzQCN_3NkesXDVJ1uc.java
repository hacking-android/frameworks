/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.DoublePredicate;
import java.util.function.Supplier;
import java.util.stream.MatchOps;

public final class _$$Lambda$MatchOps$VXR1J72V6WzQCN_3NkesXDVJ1uc
implements Supplier {
    private final /* synthetic */ MatchOps.MatchKind f$0;
    private final /* synthetic */ DoublePredicate f$1;

    public /* synthetic */ _$$Lambda$MatchOps$VXR1J72V6WzQCN_3NkesXDVJ1uc(MatchOps.MatchKind matchKind, DoublePredicate doublePredicate) {
        this.f$0 = matchKind;
        this.f$1 = doublePredicate;
    }

    public final Object get() {
        return MatchOps.lambda$makeDouble$3(this.f$0, this.f$1);
    }
}

