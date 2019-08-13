/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Objects;
import java.util.function._$$Lambda$BiPredicate$OpXxJPTnCjvZwHcfaL3bBDqxCyQ;
import java.util.function._$$Lambda$BiPredicate$_ZiDuSsQaw4dQsCoX8HU1cLSeS8;
import java.util.function._$$Lambda$BiPredicate$uIXzGqdBtyDdYjd7p0mbJFqyjRE;

@FunctionalInterface
public interface BiPredicate<T, U> {
    public static /* synthetic */ boolean lambda$and$0(BiPredicate biPredicate, BiPredicate biPredicate2, Object object, Object object2) {
        boolean bl = biPredicate.test(object, object2) && biPredicate2.test(object, object2);
        return bl;
    }

    public static /* synthetic */ boolean lambda$negate$1(BiPredicate biPredicate, Object object, Object object2) {
        return biPredicate.test(object, object2) ^ true;
    }

    public static /* synthetic */ boolean lambda$or$2(BiPredicate biPredicate, BiPredicate biPredicate2, Object object, Object object2) {
        boolean bl = biPredicate.test(object, object2) || biPredicate2.test(object, object2);
        return bl;
    }

    default public BiPredicate<T, U> and(BiPredicate<? super T, ? super U> biPredicate) {
        Objects.requireNonNull(biPredicate);
        return new _$$Lambda$BiPredicate$uIXzGqdBtyDdYjd7p0mbJFqyjRE(this, biPredicate);
    }

    default public BiPredicate<T, U> negate() {
        return new _$$Lambda$BiPredicate$_ZiDuSsQaw4dQsCoX8HU1cLSeS8(this);
    }

    default public BiPredicate<T, U> or(BiPredicate<? super T, ? super U> biPredicate) {
        Objects.requireNonNull(biPredicate);
        return new _$$Lambda$BiPredicate$OpXxJPTnCjvZwHcfaL3bBDqxCyQ(this, biPredicate);
    }

    public boolean test(T var1, U var2);
}

