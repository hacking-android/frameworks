/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.function.pooled;

import com.android.internal.util.function.pooled.PooledConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.function.Function;

public interface PooledFunction<A, R>
extends PooledLambda,
Function<A, R> {
    public PooledConsumer<A> asConsumer();

    @Override
    public PooledFunction<A, R> recycleOnUse();
}

