/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.function.pooled;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.function.HeptConsumer;
import com.android.internal.util.function.HeptFunction;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.HexFunction;
import com.android.internal.util.function.NonaConsumer;
import com.android.internal.util.function.NonaFunction;
import com.android.internal.util.function.OctConsumer;
import com.android.internal.util.function.OctFunction;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.QuintFunction;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.TriFunction;
import com.android.internal.util.function.pooled.PooledConsumer;
import com.android.internal.util.function.pooled.PooledFunction;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.internal.util.function.pooled.PooledRunnable;
import com.android.internal.util.function.pooled.PooledSupplier;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

abstract class OmniFunction<A, B, C, D, E, F, G, H, I, R>
implements PooledFunction<A, R>,
BiFunction<A, B, R>,
TriFunction<A, B, C, R>,
QuadFunction<A, B, C, D, R>,
QuintFunction<A, B, C, D, E, R>,
HexFunction<A, B, C, D, E, F, R>,
HeptFunction<A, B, C, D, E, F, G, R>,
OctFunction<A, B, C, D, E, F, G, H, R>,
NonaFunction<A, B, C, D, E, F, G, H, I, R>,
PooledConsumer<A>,
BiConsumer<A, B>,
TriConsumer<A, B, C>,
QuadConsumer<A, B, C, D>,
QuintConsumer<A, B, C, D, E>,
HexConsumer<A, B, C, D, E, F>,
HeptConsumer<A, B, C, D, E, F, G>,
OctConsumer<A, B, C, D, E, F, G, H>,
NonaConsumer<A, B, C, D, E, F, G, H, I>,
PooledPredicate<A>,
BiPredicate<A, B>,
PooledSupplier<R>,
PooledRunnable,
FunctionalUtils.ThrowingRunnable,
FunctionalUtils.ThrowingSupplier<R>,
PooledSupplier.OfInt,
PooledSupplier.OfLong,
PooledSupplier.OfDouble {
    OmniFunction() {
    }

    @Override
    public void accept(A a) {
        this.invoke(a, null, null, null, null, null, null, null, null);
    }

    @Override
    public void accept(A a, B b) {
        this.invoke(a, b, null, null, null, null, null, null, null);
    }

    @Override
    public void accept(A a, B b, C c) {
        this.invoke(a, b, c, null, null, null, null, null, null);
    }

    @Override
    public void accept(A a, B b, C c, D d) {
        this.invoke(a, b, c, d, null, null, null, null, null);
    }

    @Override
    public void accept(A a, B b, C c, D d, E e) {
        this.invoke(a, b, c, d, e, null, null, null, null);
    }

    @Override
    public void accept(A a, B b, C c, D d, E e, F f) {
        this.invoke(a, b, c, d, e, f, null, null, null);
    }

    @Override
    public void accept(A a, B b, C c, D d, E e, F f, G g) {
        this.invoke(a, b, c, d, e, f, g, null, null);
    }

    @Override
    public void accept(A a, B b, C c, D d, E e, F f, G g, H h) {
        this.invoke(a, b, c, d, e, f, g, h, null);
    }

    @Override
    public void accept(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        this.invoke(a, b, c, d, e, f, g, h, i);
    }

    public abstract <V> OmniFunction<A, B, C, D, E, F, G, H, I, V> andThen(Function<? super R, ? extends V> var1);

    @Override
    public R apply(A a) {
        return this.invoke(a, null, null, null, null, null, null, null, null);
    }

    @Override
    public R apply(A a, B b) {
        return this.invoke(a, b, null, null, null, null, null, null, null);
    }

    @Override
    public R apply(A a, B b, C c) {
        return this.invoke(a, b, c, null, null, null, null, null, null);
    }

    @Override
    public R apply(A a, B b, C c, D d) {
        return this.invoke(a, b, c, d, null, null, null, null, null);
    }

    @Override
    public R apply(A a, B b, C c, D d, E e) {
        return this.invoke(a, b, c, d, e, null, null, null, null);
    }

    @Override
    public R apply(A a, B b, C c, D d, E e, F f) {
        return this.invoke(a, b, c, d, e, f, null, null, null);
    }

    @Override
    public R apply(A a, B b, C c, D d, E e, F f, G g) {
        return this.invoke(a, b, c, d, e, f, g, null, null);
    }

    @Override
    public R apply(A a, B b, C c, D d, E e, F f, G g, H h) {
        return this.invoke(a, b, c, d, e, f, g, h, null);
    }

    @Override
    public R apply(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return this.invoke(a, b, c, d, e, f, g, h, i);
    }

    @Override
    public PooledConsumer<A> asConsumer() {
        return this;
    }

    @Override
    public PooledRunnable asRunnable() {
        return this;
    }

    @Override
    public R get() {
        return this.invoke(null, null, null, null, null, null, null, null, null);
    }

    @Override
    public R getOrThrow() throws Exception {
        return this.get();
    }

    abstract R invoke(A var1, B var2, C var3, D var4, E var5, F var6, G var7, H var8, I var9);

    public abstract OmniFunction<A, B, C, D, E, F, G, H, I, R> negate();

    @Override
    public abstract OmniFunction<A, B, C, D, E, F, G, H, I, R> recycleOnUse();

    @Override
    public void run() {
        this.invoke(null, null, null, null, null, null, null, null, null);
    }

    @Override
    public void runOrThrow() throws Exception {
        this.run();
    }

    @Override
    public boolean test(A a) {
        return (Boolean)this.invoke(a, null, null, null, null, null, null, null, null);
    }

    @Override
    public boolean test(A a, B b) {
        return (Boolean)this.invoke(a, b, null, null, null, null, null, null, null);
    }
}

