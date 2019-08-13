/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.function.pooled;

import android.os.Message;
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
import com.android.internal.util.function.pooled.ArgumentPlaceholder;
import com.android.internal.util.function.pooled.PooledConsumer;
import com.android.internal.util.function.pooled.PooledFunction;
import com.android.internal.util.function.pooled.PooledLambdaImpl;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.internal.util.function.pooled.PooledRunnable;
import com.android.internal.util.function.pooled.PooledSupplier;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface PooledLambda {
    public static <R> ArgumentPlaceholder<R> __() {
        return ArgumentPlaceholder.INSTANCE;
    }

    public static <R> ArgumentPlaceholder<R> __(Class<R> class_) {
        return PooledLambda.__();
    }

    public static <A, B, C, D> PooledConsumer<A> obtainConsumer(QuadConsumer<? super A, ? super B, ? super C, ? super D> quadConsumer, ArgumentPlaceholder<A> argumentPlaceholder, B b, C c, D d) {
        return (PooledConsumer)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadConsumer, 4, 1, 1, argumentPlaceholder, b, c, d, null, null, null, null, null);
    }

    public static <A, B, C, D> PooledConsumer<B> obtainConsumer(QuadConsumer<? super A, ? super B, ? super C, ? super D> quadConsumer, A a, ArgumentPlaceholder<B> argumentPlaceholder, C c, D d) {
        return (PooledConsumer)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadConsumer, 4, 1, 1, a, argumentPlaceholder, c, d, null, null, null, null, null);
    }

    public static <A, B, C, D> PooledConsumer<C> obtainConsumer(QuadConsumer<? super A, ? super B, ? super C, ? super D> quadConsumer, A a, B b, ArgumentPlaceholder<C> argumentPlaceholder, D d) {
        return (PooledConsumer)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadConsumer, 4, 1, 1, a, b, argumentPlaceholder, d, null, null, null, null, null);
    }

    public static <A, B, C, D> PooledConsumer<D> obtainConsumer(QuadConsumer<? super A, ? super B, ? super C, ? super D> quadConsumer, A a, B b, C c, ArgumentPlaceholder<D> argumentPlaceholder) {
        return (PooledConsumer)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadConsumer, 4, 1, 1, a, b, c, argumentPlaceholder, null, null, null, null, null);
    }

    public static <A, B, C> PooledConsumer<A> obtainConsumer(TriConsumer<? super A, ? super B, ? super C> triConsumer, ArgumentPlaceholder<A> argumentPlaceholder, B b, C c) {
        return (PooledConsumer)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, triConsumer, 3, 1, 1, argumentPlaceholder, b, c, null, null, null, null, null, null);
    }

    public static <A, B, C> PooledConsumer<B> obtainConsumer(TriConsumer<? super A, ? super B, ? super C> triConsumer, A a, ArgumentPlaceholder<B> argumentPlaceholder, C c) {
        return (PooledConsumer)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, triConsumer, 3, 1, 1, a, argumentPlaceholder, c, null, null, null, null, null, null);
    }

    public static <A, B, C> PooledConsumer<C> obtainConsumer(TriConsumer<? super A, ? super B, ? super C> triConsumer, A a, B b, ArgumentPlaceholder<C> argumentPlaceholder) {
        return (PooledConsumer)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, triConsumer, 3, 1, 1, a, b, argumentPlaceholder, null, null, null, null, null, null);
    }

    public static <A, B> PooledConsumer<A> obtainConsumer(BiConsumer<? super A, ? super B> biConsumer, ArgumentPlaceholder<A> argumentPlaceholder, B b) {
        return (PooledConsumer)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biConsumer, 2, 1, 1, argumentPlaceholder, b, null, null, null, null, null, null, null);
    }

    public static <A, B> PooledConsumer<B> obtainConsumer(BiConsumer<? super A, ? super B> biConsumer, A a, ArgumentPlaceholder<B> argumentPlaceholder) {
        return (PooledConsumer)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biConsumer, 2, 1, 1, a, argumentPlaceholder, null, null, null, null, null, null, null);
    }

    public static <A, B, C, D, R> PooledFunction<A, R> obtainFunction(QuadFunction<? super A, ? super B, ? super C, ? super D, ? extends R> quadFunction, ArgumentPlaceholder<A> argumentPlaceholder, B b, C c, D d) {
        return (PooledFunction)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadFunction, 4, 1, 3, argumentPlaceholder, b, c, d, null, null, null, null, null);
    }

    public static <A, B, C, D, R> PooledFunction<B, R> obtainFunction(QuadFunction<? super A, ? super B, ? super C, ? super D, ? extends R> quadFunction, A a, ArgumentPlaceholder<B> argumentPlaceholder, C c, D d) {
        return (PooledFunction)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadFunction, 4, 1, 3, a, argumentPlaceholder, c, d, null, null, null, null, null);
    }

    public static <A, B, C, D, R> PooledFunction<C, R> obtainFunction(QuadFunction<? super A, ? super B, ? super C, ? super D, ? extends R> quadFunction, A a, B b, ArgumentPlaceholder<C> argumentPlaceholder, D d) {
        return (PooledFunction)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadFunction, 4, 1, 3, a, b, argumentPlaceholder, d, null, null, null, null, null);
    }

    public static <A, B, C, D, R> PooledFunction<D, R> obtainFunction(QuadFunction<? super A, ? super B, ? super C, ? super D, ? extends R> quadFunction, A a, B b, C c, ArgumentPlaceholder<D> argumentPlaceholder) {
        return (PooledFunction)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadFunction, 4, 1, 3, a, b, c, argumentPlaceholder, null, null, null, null, null);
    }

    public static <A, B, C, R> PooledFunction<A, R> obtainFunction(TriFunction<? super A, ? super B, ? super C, ? extends R> triFunction, ArgumentPlaceholder<A> argumentPlaceholder, B b, C c) {
        return (PooledFunction)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, triFunction, 3, 1, 3, argumentPlaceholder, b, c, null, null, null, null, null, null);
    }

    public static <A, B, C, R> PooledFunction<B, R> obtainFunction(TriFunction<? super A, ? super B, ? super C, ? extends R> triFunction, A a, ArgumentPlaceholder<B> argumentPlaceholder, C c) {
        return (PooledFunction)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, triFunction, 3, 1, 3, a, argumentPlaceholder, c, null, null, null, null, null, null);
    }

    public static <A, B, C, R> PooledFunction<C, R> obtainFunction(TriFunction<? super A, ? super B, ? super C, ? extends R> triFunction, A a, B b, ArgumentPlaceholder<C> argumentPlaceholder) {
        return (PooledFunction)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, triFunction, 3, 1, 3, a, b, argumentPlaceholder, null, null, null, null, null, null);
    }

    public static <A, B, R> PooledFunction<A, R> obtainFunction(BiFunction<? super A, ? super B, ? extends R> biFunction, ArgumentPlaceholder<A> argumentPlaceholder, B b) {
        return (PooledFunction)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biFunction, 2, 1, 3, argumentPlaceholder, b, null, null, null, null, null, null, null);
    }

    public static <A, B, R> PooledFunction<B, R> obtainFunction(BiFunction<? super A, ? super B, ? extends R> biFunction, A a, ArgumentPlaceholder<B> argumentPlaceholder) {
        return (PooledFunction)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biFunction, 2, 1, 3, a, argumentPlaceholder, null, null, null, null, null, null, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <A, B, C, D, E, F, G> Message obtainMessage(HeptConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> object, A a, B b, C c, D d, E e, F f, G g) {
        Object object2 = Message.sPoolSync;
        synchronized (object2) {
            object = (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, object, 7, 0, 1, a, b, c, d, e, f, g, null, null);
            return Message.obtain().setCallback((Runnable)((Object)object.recycleOnUse()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <A, B, C, D, E, F> Message obtainMessage(HexConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> object, A a, B b, C c, D d, E e, F f) {
        Object object2 = Message.sPoolSync;
        synchronized (object2) {
            object = (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, object, 6, 0, 1, a, b, c, d, e, f, null, null, null);
            return Message.obtain().setCallback((Runnable)((Object)object.recycleOnUse()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <A, B, C, D, E, F, G, H, I> Message obtainMessage(NonaConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I> object, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        Object object2 = Message.sPoolSync;
        synchronized (object2) {
            object = (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, object, 9, 0, 1, a, b, c, d, e, f, g, h, i);
            return Message.obtain().setCallback((Runnable)((Object)object.recycleOnUse()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <A, B, C, D, E, F, G, H> Message obtainMessage(OctConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> object, A a, B b, C c, D d, E e, F f, G g, H h) {
        Object object2 = Message.sPoolSync;
        synchronized (object2) {
            object = (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, object, 8, 0, 1, a, b, c, d, e, f, g, h, null);
            return Message.obtain().setCallback((Runnable)((Object)object.recycleOnUse()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <A, B, C, D> Message obtainMessage(QuadConsumer<? super A, ? super B, ? super C, ? super D> object, A a, B b, C c, D d) {
        Object object2 = Message.sPoolSync;
        synchronized (object2) {
            object = (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, object, 4, 0, 1, a, b, c, d, null, null, null, null, null);
            return Message.obtain().setCallback((Runnable)((Object)object.recycleOnUse()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <A, B, C, D, E> Message obtainMessage(QuintConsumer<? super A, ? super B, ? super C, ? super D, ? super E> object, A a, B b, C c, D d, E e) {
        Object object2 = Message.sPoolSync;
        synchronized (object2) {
            object = (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, object, 5, 0, 1, a, b, c, d, e, null, null, null, null);
            return Message.obtain().setCallback((Runnable)((Object)object.recycleOnUse()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <A, B, C> Message obtainMessage(TriConsumer<? super A, ? super B, ? super C> object, A a, B b, C c) {
        Object object2 = Message.sPoolSync;
        synchronized (object2) {
            object = (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, object, 3, 0, 1, a, b, c, null, null, null, null, null, null);
            return Message.obtain().setCallback((Runnable)((Object)object.recycleOnUse()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <A, B> Message obtainMessage(BiConsumer<? super A, ? super B> object, A a, B b) {
        Object object2 = Message.sPoolSync;
        synchronized (object2) {
            object = (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, object, 2, 0, 1, a, b, null, null, null, null, null, null, null);
            return Message.obtain().setCallback((Runnable)((Object)object.recycleOnUse()));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <A> Message obtainMessage(Consumer<? super A> object, A a) {
        Object object2 = Message.sPoolSync;
        synchronized (object2) {
            object = (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, object, 1, 0, 1, a, null, null, null, null, null, null, null, null);
            return Message.obtain().setCallback((Runnable)((Object)object.recycleOnUse()));
        }
    }

    public static <A, B> PooledPredicate<A> obtainPredicate(BiPredicate<? super A, ? super B> biPredicate, ArgumentPlaceholder<A> argumentPlaceholder, B b) {
        return (PooledPredicate)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biPredicate, 2, 1, 2, argumentPlaceholder, b, null, null, null, null, null, null, null);
    }

    public static <A, B> PooledPredicate<B> obtainPredicate(BiPredicate<? super A, ? super B> biPredicate, A a, ArgumentPlaceholder<B> argumentPlaceholder) {
        return (PooledPredicate)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biPredicate, 2, 1, 2, a, argumentPlaceholder, null, null, null, null, null, null, null);
    }

    public static <A, B, C, D, E, F, G> PooledRunnable obtainRunnable(HeptConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> heptConsumer, A a, B b, C c, D d, E e, F f, G g) {
        return (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, heptConsumer, 7, 0, 1, a, b, c, d, e, f, g, null, null);
    }

    public static <A, B, C, D, E, F> PooledRunnable obtainRunnable(HexConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> hexConsumer, A a, B b, C c, D d, E e, F f) {
        return (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, hexConsumer, 6, 0, 1, a, b, c, d, e, f, null, null, null);
    }

    public static <A, B, C, D, E, F, G, H, I> PooledRunnable obtainRunnable(NonaConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I> nonaConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, nonaConsumer, 9, 0, 1, a, b, c, d, e, f, g, h, i);
    }

    public static <A, B, C, D, E, F, G, H> PooledRunnable obtainRunnable(OctConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> octConsumer, A a, B b, C c, D d, E e, F f, G g, H h) {
        return (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, octConsumer, 8, 0, 1, a, b, c, d, e, f, g, h, null);
    }

    public static <A, B, C, D> PooledRunnable obtainRunnable(QuadConsumer<? super A, ? super B, ? super C, ? super D> quadConsumer, A a, B b, C c, D d) {
        return (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadConsumer, 4, 0, 1, a, b, c, d, null, null, null, null, null);
    }

    public static <A, B, C, D, E> PooledRunnable obtainRunnable(QuintConsumer<? super A, ? super B, ? super C, ? super D, ? super E> quintConsumer, A a, B b, C c, D d, E e) {
        return (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quintConsumer, 5, 0, 1, a, b, c, d, e, null, null, null, null);
    }

    public static <A, B, C> PooledRunnable obtainRunnable(TriConsumer<? super A, ? super B, ? super C> triConsumer, A a, B b, C c) {
        return (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, triConsumer, 3, 0, 1, a, b, c, null, null, null, null, null, null);
    }

    public static <A, B> PooledRunnable obtainRunnable(BiConsumer<? super A, ? super B> biConsumer, A a, B b) {
        return (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biConsumer, 2, 0, 1, a, b, null, null, null, null, null, null, null);
    }

    public static <A> PooledRunnable obtainRunnable(Consumer<? super A> consumer, A a) {
        return (PooledRunnable)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, consumer, 1, 0, 1, a, null, null, null, null, null, null, null, null);
    }

    public static PooledSupplier.OfDouble obtainSupplier(double d) {
        PooledLambdaImpl pooledLambdaImpl = PooledLambdaImpl.acquireConstSupplier(6);
        pooledLambdaImpl.mConstValue = Double.doubleToRawLongBits(d);
        return pooledLambdaImpl;
    }

    public static PooledSupplier.OfInt obtainSupplier(int n) {
        PooledLambdaImpl pooledLambdaImpl = PooledLambdaImpl.acquireConstSupplier(4);
        pooledLambdaImpl.mConstValue = n;
        return pooledLambdaImpl;
    }

    public static PooledSupplier.OfLong obtainSupplier(long l) {
        PooledLambdaImpl pooledLambdaImpl = PooledLambdaImpl.acquireConstSupplier(5);
        pooledLambdaImpl.mConstValue = l;
        return pooledLambdaImpl;
    }

    public static <A, B, C, D, E, F, G, R> PooledSupplier<R> obtainSupplier(HeptFunction<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? extends R> heptFunction, A a, B b, C c, D d, E e, F f, G g) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, heptFunction, 7, 0, 3, a, b, c, d, e, f, g, null, null);
    }

    public static <A, B, C, D, E, F, R> PooledSupplier<R> obtainSupplier(HexFunction<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? extends R> hexFunction, A a, B b, C c, D d, E e, F f) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, hexFunction, 6, 0, 3, a, b, c, d, e, f, null, null, null);
    }

    public static <A, B, C, D, E, F, G, H, I, R> PooledSupplier<R> obtainSupplier(NonaFunction<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? extends R> nonaFunction, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, nonaFunction, 9, 0, 3, a, b, c, d, e, f, g, h, i);
    }

    public static <A, B, C, D, E, F, G, H, R> PooledSupplier<R> obtainSupplier(OctFunction<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? extends R> octFunction, A a, B b, C c, D d, E e, F f, G g, H h) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, octFunction, 8, 0, 3, a, b, c, d, e, f, g, h, null);
    }

    public static <A, B, C, D, R> PooledSupplier<R> obtainSupplier(QuadFunction<? super A, ? super B, ? super C, ? super D, ? extends R> quadFunction, A a, B b, C c, D d) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadFunction, 4, 0, 3, a, b, c, d, null, null, null, null, null);
    }

    public static <A, B, C, D, E, R> PooledSupplier<R> obtainSupplier(QuintFunction<? super A, ? super B, ? super C, ? super D, ? super E, ? extends R> quintFunction, A a, B b, C c, D d, E e) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quintFunction, 5, 0, 3, a, b, c, d, e, null, null, null, null);
    }

    public static <A, B, C, R> PooledSupplier<R> obtainSupplier(TriFunction<? super A, ? super B, ? super C, ? extends R> triFunction, A a, B b, C c) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, triFunction, 3, 0, 3, a, b, c, null, null, null, null, null, null);
    }

    public static <R> PooledSupplier<R> obtainSupplier(R r) {
        PooledLambdaImpl pooledLambdaImpl = PooledLambdaImpl.acquireConstSupplier(3);
        pooledLambdaImpl.mFunc = r;
        return pooledLambdaImpl;
    }

    public static <A, B, R> PooledSupplier<R> obtainSupplier(BiFunction<? super A, ? super B, ? extends R> biFunction, A a, B b) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biFunction, 2, 0, 3, a, b, null, null, null, null, null, null, null);
    }

    public static <A, B> PooledSupplier<Boolean> obtainSupplier(BiPredicate<? super A, ? super B> biPredicate, A a, B b) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biPredicate, 2, 0, 2, a, b, null, null, null, null, null, null, null);
    }

    public static <A, R> PooledSupplier<R> obtainSupplier(Function<? super A, ? extends R> function, A a) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, function, 1, 0, 3, a, null, null, null, null, null, null, null, null);
    }

    public static <A> PooledSupplier<Boolean> obtainSupplier(Predicate<? super A> predicate, A a) {
        return (PooledSupplier)PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, predicate, 1, 0, 2, a, null, null, null, null, null, null, null, null);
    }

    public void recycle();

    public PooledLambda recycleOnUse();
}

