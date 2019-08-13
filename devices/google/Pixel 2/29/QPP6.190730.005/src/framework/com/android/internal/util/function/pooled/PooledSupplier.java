/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.function.pooled;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledRunnable;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

public interface PooledSupplier<T>
extends PooledLambda,
Supplier<T>,
FunctionalUtils.ThrowingSupplier<T> {
    public PooledRunnable asRunnable();

    @Override
    public PooledSupplier<T> recycleOnUse();

    public static interface OfDouble
    extends DoubleSupplier,
    PooledLambda {
        @Override
        public OfDouble recycleOnUse();
    }

    public static interface OfInt
    extends IntSupplier,
    PooledLambda {
        @Override
        public OfInt recycleOnUse();
    }

    public static interface OfLong
    extends LongSupplier,
    PooledLambda {
        @Override
        public OfLong recycleOnUse();
    }

}

