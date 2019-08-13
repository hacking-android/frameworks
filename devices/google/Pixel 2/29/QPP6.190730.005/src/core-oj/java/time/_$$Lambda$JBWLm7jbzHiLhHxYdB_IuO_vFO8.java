/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$JBWLm7jbzHiLhHxYdB_IuO_vFO8
 */
package java.time;

import java.time.-$;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public final class _$$Lambda$JBWLm7jbzHiLhHxYdB_IuO_vFO8
implements TemporalQuery {
    public static final /* synthetic */ -$.Lambda.JBWLm7jbzHiLhHxYdB_IuO_vFO8 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$JBWLm7jbzHiLhHxYdB_IuO_vFO8();
    }

    private /* synthetic */ _$$Lambda$JBWLm7jbzHiLhHxYdB_IuO_vFO8() {
    }

    public final Object queryFrom(TemporalAccessor temporalAccessor) {
        return LocalDateTime.from(temporalAccessor);
    }
}

