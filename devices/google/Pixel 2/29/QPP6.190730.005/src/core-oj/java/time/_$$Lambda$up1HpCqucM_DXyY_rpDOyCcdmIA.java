/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$up1HpCqucM_DXyY-rpDOyCcdmIA
 */
package java.time;

import java.time.-$;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public final class _$$Lambda$up1HpCqucM_DXyY_rpDOyCcdmIA
implements TemporalQuery {
    public static final /* synthetic */ -$.Lambda.up1HpCqucM_DXyY-rpDOyCcdmIA INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$up1HpCqucM_DXyY_rpDOyCcdmIA();
    }

    private /* synthetic */ _$$Lambda$up1HpCqucM_DXyY_rpDOyCcdmIA() {
    }

    public final Object queryFrom(TemporalAccessor temporalAccessor) {
        return ZonedDateTime.from(temporalAccessor);
    }
}

