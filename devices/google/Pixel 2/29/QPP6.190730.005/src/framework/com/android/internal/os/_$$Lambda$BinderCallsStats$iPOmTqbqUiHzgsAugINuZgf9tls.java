/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.os.-$
 *  com.android.internal.os.-$$Lambda
 *  com.android.internal.os.-$$Lambda$BinderCallsStats
 *  com.android.internal.os.-$$Lambda$BinderCallsStats$iPOmTqbqUiHzgsAugINuZgf9tls
 */
package com.android.internal.os;

import com.android.internal.os.-$;
import com.android.internal.os.BinderCallsStats;
import java.util.function.ToDoubleFunction;

public final class _$$Lambda$BinderCallsStats$iPOmTqbqUiHzgsAugINuZgf9tls
implements ToDoubleFunction {
    public static final /* synthetic */ -$.Lambda.BinderCallsStats.iPOmTqbqUiHzgsAugINuZgf9tls INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$BinderCallsStats$iPOmTqbqUiHzgsAugINuZgf9tls();
    }

    private /* synthetic */ _$$Lambda$BinderCallsStats$iPOmTqbqUiHzgsAugINuZgf9tls() {
    }

    public final double applyAsDouble(Object object) {
        return BinderCallsStats.lambda$dumpLocked$0((BinderCallsStats.UidEntry)object);
    }
}

