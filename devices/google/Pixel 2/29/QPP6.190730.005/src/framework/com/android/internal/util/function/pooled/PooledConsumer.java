/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.function.pooled;

import com.android.internal.util.function.pooled.PooledLambda;
import java.util.function.Consumer;

public interface PooledConsumer<T>
extends PooledLambda,
Consumer<T> {
    @Override
    public PooledConsumer<T> recycleOnUse();
}

