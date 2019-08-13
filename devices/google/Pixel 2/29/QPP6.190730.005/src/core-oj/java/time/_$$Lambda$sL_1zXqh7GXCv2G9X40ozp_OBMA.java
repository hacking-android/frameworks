/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$sL_1zXqh7GXCv2G9X40ozp_OBMA
 */
package java.time;

import java.time.-$;
import java.time.MonthDay;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public final class _$$Lambda$sL_1zXqh7GXCv2G9X40ozp_OBMA
implements TemporalQuery {
    public static final /* synthetic */ -$.Lambda.sL_1zXqh7GXCv2G9X40ozp_OBMA INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$sL_1zXqh7GXCv2G9X40ozp_OBMA();
    }

    private /* synthetic */ _$$Lambda$sL_1zXqh7GXCv2G9X40ozp_OBMA() {
    }

    public final Object queryFrom(TemporalAccessor temporalAccessor) {
        return MonthDay.from(temporalAccessor);
    }
}

