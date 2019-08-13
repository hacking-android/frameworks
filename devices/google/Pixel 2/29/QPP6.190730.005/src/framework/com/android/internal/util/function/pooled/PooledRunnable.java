/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.function.pooled;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.function.pooled.PooledLambda;

public interface PooledRunnable
extends PooledLambda,
Runnable,
FunctionalUtils.ThrowingRunnable {
    @Override
    public PooledRunnable recycleOnUse();
}

