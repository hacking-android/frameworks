/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.LongPredicate;

public final class _$$Lambda$LongPredicate$Qy6TdFCh7weCJdG_MkUTh3wmWoA
implements LongPredicate {
    private final /* synthetic */ LongPredicate f$0;

    public /* synthetic */ _$$Lambda$LongPredicate$Qy6TdFCh7weCJdG_MkUTh3wmWoA(LongPredicate longPredicate) {
        this.f$0 = longPredicate;
    }

    @Override
    public final boolean test(long l) {
        return LongPredicate.lambda$negate$1(this.f$0, l);
    }
}

