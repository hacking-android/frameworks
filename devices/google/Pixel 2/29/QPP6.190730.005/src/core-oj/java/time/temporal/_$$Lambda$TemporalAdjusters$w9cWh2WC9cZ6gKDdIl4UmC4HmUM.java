/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.temporal.-$
 *  java.time.temporal.-$$Lambda
 *  java.time.temporal.-$$Lambda$TemporalAdjusters
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$w9cWh2WC9cZ6gKDdIl4UmC4HmUM
 */
package java.time.temporal;

import java.time.temporal.-$;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public final class _$$Lambda$TemporalAdjusters$w9cWh2WC9cZ6gKDdIl4UmC4HmUM
implements TemporalAdjuster {
    public static final /* synthetic */ -$.Lambda.TemporalAdjusters.w9cWh2WC9cZ6gKDdIl4UmC4HmUM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$TemporalAdjusters$w9cWh2WC9cZ6gKDdIl4UmC4HmUM();
    }

    private /* synthetic */ _$$Lambda$TemporalAdjusters$w9cWh2WC9cZ6gKDdIl4UmC4HmUM() {
    }

    @Override
    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$firstDayOfYear$4(temporal);
    }
}

