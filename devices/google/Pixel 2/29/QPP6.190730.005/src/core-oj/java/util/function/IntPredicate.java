/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Objects;
import java.util.function._$$Lambda$IntPredicate$6LuiLiTSEVs3MpquRl2gnnnEIxg;
import java.util.function._$$Lambda$IntPredicate$Gjqjw1UkLLbkSrWX6rKKkHJDvzI;
import java.util.function._$$Lambda$IntPredicate$K711jAs3Mu_dbXoV61T3AbYlIaU;

@FunctionalInterface
public interface IntPredicate {
    public static /* synthetic */ boolean lambda$and$0(IntPredicate intPredicate, IntPredicate intPredicate2, int n) {
        boolean bl = intPredicate.test(n) && intPredicate2.test(n);
        return bl;
    }

    public static /* synthetic */ boolean lambda$negate$1(IntPredicate intPredicate, int n) {
        return intPredicate.test(n) ^ true;
    }

    public static /* synthetic */ boolean lambda$or$2(IntPredicate intPredicate, IntPredicate intPredicate2, int n) {
        boolean bl = intPredicate.test(n) || intPredicate2.test(n);
        return bl;
    }

    default public IntPredicate and(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new _$$Lambda$IntPredicate$Gjqjw1UkLLbkSrWX6rKKkHJDvzI(this, intPredicate);
    }

    default public IntPredicate negate() {
        return new _$$Lambda$IntPredicate$6LuiLiTSEVs3MpquRl2gnnnEIxg(this);
    }

    default public IntPredicate or(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new _$$Lambda$IntPredicate$K711jAs3Mu_dbXoV61T3AbYlIaU(this, intPredicate);
    }

    public boolean test(int var1);
}

