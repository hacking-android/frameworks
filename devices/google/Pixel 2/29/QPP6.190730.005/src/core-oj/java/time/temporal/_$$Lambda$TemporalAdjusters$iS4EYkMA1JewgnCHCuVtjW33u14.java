/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.temporal.-$
 *  java.time.temporal.-$$Lambda
 *  java.time.temporal.-$$Lambda$TemporalAdjusters
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$iS4EYkMA1JewgnCHCuVtjW33u14
 */
package java.time.temporal;

import java.time.temporal.-$;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public final class _$$Lambda$TemporalAdjusters$iS4EYkMA1JewgnCHCuVtjW33u14
implements TemporalAdjuster {
    public static final /* synthetic */ -$.Lambda.TemporalAdjusters.iS4EYkMA1JewgnCHCuVtjW33u14 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$TemporalAdjusters$iS4EYkMA1JewgnCHCuVtjW33u14();
    }

    private /* synthetic */ _$$Lambda$TemporalAdjusters$iS4EYkMA1JewgnCHCuVtjW33u14() {
    }

    @Override
    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$lastDayOfYear$5(temporal);
    }
}

