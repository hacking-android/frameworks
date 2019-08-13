/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$1t2bycXU085eFZcwODXkbd0X4Bk
 */
package java.time;

import java.time.-$;
import java.time.Year;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public final class _$$Lambda$1t2bycXU085eFZcwODXkbd0X4Bk
implements TemporalQuery {
    public static final /* synthetic */ -$.Lambda.1t2bycXU085eFZcwODXkbd0X4Bk INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$1t2bycXU085eFZcwODXkbd0X4Bk();
    }

    private /* synthetic */ _$$Lambda$1t2bycXU085eFZcwODXkbd0X4Bk() {
    }

    public final Object queryFrom(TemporalAccessor temporalAccessor) {
        return Year.from(temporalAccessor);
    }
}

