/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.MatchOps;

public final class _$$Lambda$MatchOps$_LtFSpSMfVwoPv_8p_1cMGGcaHA
implements Supplier {
    private final /* synthetic */ MatchOps.MatchKind f$0;
    private final /* synthetic */ Predicate f$1;

    public /* synthetic */ _$$Lambda$MatchOps$_LtFSpSMfVwoPv_8p_1cMGGcaHA(MatchOps.MatchKind matchKind, Predicate predicate) {
        this.f$0 = matchKind;
        this.f$1 = predicate;
    }

    public final Object get() {
        return MatchOps.lambda$makeRef$0(this.f$0, this.f$1);
    }
}

