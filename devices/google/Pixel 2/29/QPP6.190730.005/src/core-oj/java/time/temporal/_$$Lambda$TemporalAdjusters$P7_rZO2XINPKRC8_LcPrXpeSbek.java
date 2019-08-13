/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.temporal.-$
 *  java.time.temporal.-$$Lambda
 *  java.time.temporal.-$$Lambda$TemporalAdjusters
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$P7_rZO2XINPKRC8_LcPrXpeSbek
 */
package java.time.temporal;

import java.time.temporal.-$;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public final class _$$Lambda$TemporalAdjusters$P7_rZO2XINPKRC8_LcPrXpeSbek
implements TemporalAdjuster {
    public static final /* synthetic */ -$.Lambda.TemporalAdjusters.P7_rZO2XINPKRC8_LcPrXpeSbek INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$TemporalAdjusters$P7_rZO2XINPKRC8_LcPrXpeSbek();
    }

    private /* synthetic */ _$$Lambda$TemporalAdjusters$P7_rZO2XINPKRC8_LcPrXpeSbek() {
    }

    @Override
    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$firstDayOfNextMonth$3(temporal);
    }
}

