/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.os.-$
 *  com.android.internal.os.-$$Lambda
 *  com.android.internal.os.-$$Lambda$BinderCallsStats
 *  com.android.internal.os.-$$Lambda$BinderCallsStats$-YP-7pwoNn8TN0iTmo5Q1r2lQz0
 */
package com.android.internal.os;

import android.util.Pair;
import com.android.internal.os.-$;
import com.android.internal.os.BinderCallsStats;
import java.util.Comparator;

public final class _$$Lambda$BinderCallsStats$_YP_7pwoNn8TN0iTmo5Q1r2lQz0
implements Comparator {
    public static final /* synthetic */ -$.Lambda.BinderCallsStats.-YP-7pwoNn8TN0iTmo5Q1r2lQz0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$BinderCallsStats$_YP_7pwoNn8TN0iTmo5Q1r2lQz0();
    }

    private /* synthetic */ _$$Lambda$BinderCallsStats$_YP_7pwoNn8TN0iTmo5Q1r2lQz0() {
    }

    public final int compare(Object object, Object object2) {
        return BinderCallsStats.lambda$dumpLocked$3((Pair)object, (Pair)object2);
    }
}

