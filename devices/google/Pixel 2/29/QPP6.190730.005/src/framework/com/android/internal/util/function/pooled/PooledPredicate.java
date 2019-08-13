/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.function.pooled;

import com.android.internal.util.function.pooled.PooledConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.function.Predicate;

public interface PooledPredicate<T>
extends PooledLambda,
Predicate<T> {
    public PooledConsumer<T> asConsumer();

    @Override
    public PooledPredicate<T> recycleOnUse();
}

