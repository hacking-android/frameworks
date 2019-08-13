/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.os.-$
 *  com.android.internal.os.-$$Lambda
 *  com.android.internal.os.-$$Lambda$BinderCallsStats
 *  com.android.internal.os.-$$Lambda$BinderCallsStats$xI0E0RpviGYsokEB7ojNx8LEbUc
 */
package com.android.internal.os;

import com.android.internal.os.-$;
import com.android.internal.os.BinderCallsStats;
import java.util.function.ToDoubleFunction;

public final class _$$Lambda$BinderCallsStats$xI0E0RpviGYsokEB7ojNx8LEbUc
implements ToDoubleFunction {
    public static final /* synthetic */ -$.Lambda.BinderCallsStats.xI0E0RpviGYsokEB7ojNx8LEbUc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$BinderCallsStats$xI0E0RpviGYsokEB7ojNx8LEbUc();
    }

    private /* synthetic */ _$$Lambda$BinderCallsStats$xI0E0RpviGYsokEB7ojNx8LEbUc() {
    }

    public final double applyAsDouble(Object object) {
        return BinderCallsStats.lambda$dumpLocked$1((BinderCallsStats.UidEntry)object);
    }
}

