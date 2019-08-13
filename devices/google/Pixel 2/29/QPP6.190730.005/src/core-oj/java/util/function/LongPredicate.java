/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Objects;
import java.util.function._$$Lambda$LongPredicate$DaAkBBvcfHLiOGrLHthRI1hFBUQ;
import java.util.function._$$Lambda$LongPredicate$Qy6TdFCh7weCJdG_MkUTh3wmWoA;
import java.util.function._$$Lambda$LongPredicate$_csV2YowuwbIk10M6jhbu2oq5to;

@FunctionalInterface
public interface LongPredicate {
    public static /* synthetic */ boolean lambda$and$0(LongPredicate longPredicate, LongPredicate longPredicate2, long l) {
        boolean bl = longPredicate.test(l) && longPredicate2.test(l);
        return bl;
    }

    public static /* synthetic */ boolean lambda$negate$1(LongPredicate longPredicate, long l) {
        return longPredicate.test(l) ^ true;
    }

    public static /* synthetic */ boolean lambda$or$2(LongPredicate longPredicate, LongPredicate longPredicate2, long l) {
        boolean bl = longPredicate.test(l) || longPredicate2.test(l);
        return bl;
    }

    default public LongPredicate and(LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new _$$Lambda$LongPredicate$DaAkBBvcfHLiOGrLHthRI1hFBUQ(this, longPredicate);
    }

    default public LongPredicate negate() {
        return new _$$Lambda$LongPredicate$Qy6TdFCh7weCJdG_MkUTh3wmWoA(this);
    }

    default public LongPredicate or(LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new _$$Lambda$LongPredicate$_csV2YowuwbIk10M6jhbu2oq5to(this, longPredicate);
    }

    public boolean test(long var1);
}

