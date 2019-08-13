/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$I08rBDhAPdxOG_R3AeLRKYX7Z-o
 */
package java.time;

import java.time.-$;
import java.time.OffsetTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public final class _$$Lambda$I08rBDhAPdxOG_R3AeLRKYX7Z_o
implements TemporalQuery {
    public static final /* synthetic */ -$.Lambda.I08rBDhAPdxOG_R3AeLRKYX7Z-o INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$I08rBDhAPdxOG_R3AeLRKYX7Z_o();
    }

    private /* synthetic */ _$$Lambda$I08rBDhAPdxOG_R3AeLRKYX7Z_o() {
    }

    public final Object queryFrom(TemporalAccessor temporalAccessor) {
        return OffsetTime.from(temporalAccessor);
    }
}

