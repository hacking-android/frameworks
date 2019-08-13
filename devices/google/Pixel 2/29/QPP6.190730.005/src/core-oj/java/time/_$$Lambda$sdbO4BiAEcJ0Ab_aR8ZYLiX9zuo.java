/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$sdbO4BiAEcJ0Ab-aR8ZYLiX9zuo
 */
package java.time;

import java.time.-$;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public final class _$$Lambda$sdbO4BiAEcJ0Ab_aR8ZYLiX9zuo
implements TemporalQuery {
    public static final /* synthetic */ -$.Lambda.sdbO4BiAEcJ0Ab-aR8ZYLiX9zuo INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$sdbO4BiAEcJ0Ab_aR8ZYLiX9zuo();
    }

    private /* synthetic */ _$$Lambda$sdbO4BiAEcJ0Ab_aR8ZYLiX9zuo() {
    }

    public final Object queryFrom(TemporalAccessor temporalAccessor) {
        return OffsetDateTime.from(temporalAccessor);
    }
}

