/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.temporal.-$
 *  java.time.temporal.-$$Lambda
 *  java.time.temporal.-$$Lambda$TemporalAdjusters
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$WAuzLMCz-2-SwnlcREz0tiYj3XA
 */
package java.time.temporal;

import java.time.temporal.-$;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public final class _$$Lambda$TemporalAdjusters$WAuzLMCz_2_SwnlcREz0tiYj3XA
implements TemporalAdjuster {
    public static final /* synthetic */ -$.Lambda.TemporalAdjusters.WAuzLMCz-2-SwnlcREz0tiYj3XA INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$TemporalAdjusters$WAuzLMCz_2_SwnlcREz0tiYj3XA();
    }

    private /* synthetic */ _$$Lambda$TemporalAdjusters$WAuzLMCz_2_SwnlcREz0tiYj3XA() {
    }

    @Override
    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$lastDayOfMonth$2(temporal);
    }
}

