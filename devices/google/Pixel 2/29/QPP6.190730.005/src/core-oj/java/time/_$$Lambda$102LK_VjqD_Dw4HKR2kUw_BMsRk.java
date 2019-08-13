/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$102LK-VjqD_Dw4HKR2kUw-BMsRk
 */
package java.time;

import java.time.-$;
import java.time.YearMonth;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public final class _$$Lambda$102LK_VjqD_Dw4HKR2kUw_BMsRk
implements TemporalQuery {
    public static final /* synthetic */ -$.Lambda.102LK-VjqD_Dw4HKR2kUw-BMsRk INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$102LK_VjqD_Dw4HKR2kUw_BMsRk();
    }

    private /* synthetic */ _$$Lambda$102LK_VjqD_Dw4HKR2kUw_BMsRk() {
    }

    public final Object queryFrom(TemporalAccessor temporalAccessor) {
        return YearMonth.from(temporalAccessor);
    }
}

