/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Objects;
import java.util.function._$$Lambda$DoublePredicate$01E7YsTWsjaQSI72YV852C1Uqco;
import java.util.function._$$Lambda$DoublePredicate$9YmJG7lS_NUbb1veFxbs9aIWObk;
import java.util.function._$$Lambda$DoublePredicate$M8n9M3rXNLuHyZ2e0F7hUxAtVx0;

@FunctionalInterface
public interface DoublePredicate {
    public static /* synthetic */ boolean lambda$and$0(DoublePredicate doublePredicate, DoublePredicate doublePredicate2, double d) {
        boolean bl = doublePredicate.test(d) && doublePredicate2.test(d);
        return bl;
    }

    public static /* synthetic */ boolean lambda$negate$1(DoublePredicate doublePredicate, double d) {
        return doublePredicate.test(d) ^ true;
    }

    public static /* synthetic */ boolean lambda$or$2(DoublePredicate doublePredicate, DoublePredicate doublePredicate2, double d) {
        boolean bl = doublePredicate.test(d) || doublePredicate2.test(d);
        return bl;
    }

    default public DoublePredicate and(DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new _$$Lambda$DoublePredicate$M8n9M3rXNLuHyZ2e0F7hUxAtVx0(this, doublePredicate);
    }

    default public DoublePredicate negate() {
        return new _$$Lambda$DoublePredicate$01E7YsTWsjaQSI72YV852C1Uqco(this);
    }

    default public DoublePredicate or(DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new _$$Lambda$DoublePredicate$9YmJG7lS_NUbb1veFxbs9aIWObk(this, doublePredicate);
    }

    public boolean test(double var1);
}

