/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$2Dm_gBEmfWAFyI8wDj_HTrgAgUc
 */
package java.time;

import java.time.-$;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public final class _$$Lambda$2Dm_gBEmfWAFyI8wDj_HTrgAgUc
implements TemporalQuery {
    public static final /* synthetic */ -$.Lambda.2Dm_gBEmfWAFyI8wDj_HTrgAgUc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$2Dm_gBEmfWAFyI8wDj_HTrgAgUc();
    }

    private /* synthetic */ _$$Lambda$2Dm_gBEmfWAFyI8wDj_HTrgAgUc() {
    }

    public final Object queryFrom(TemporalAccessor temporalAccessor) {
        return LocalTime.from(temporalAccessor);
    }
}

