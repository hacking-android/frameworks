/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$PTL8WkLA4o-1z4zIUBjrvwi808w
 */
package java.time;

import java.time.-$;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public final class _$$Lambda$PTL8WkLA4o_1z4zIUBjrvwi808w
implements TemporalQuery {
    public static final /* synthetic */ -$.Lambda.PTL8WkLA4o-1z4zIUBjrvwi808w INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$PTL8WkLA4o_1z4zIUBjrvwi808w();
    }

    private /* synthetic */ _$$Lambda$PTL8WkLA4o_1z4zIUBjrvwi808w() {
    }

    public final Object queryFrom(TemporalAccessor temporalAccessor) {
        return Instant.from(temporalAccessor);
    }
}

