/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.temporal.-$
 *  java.time.temporal.-$$Lambda
 *  java.time.temporal.-$$Lambda$TemporalAdjusters
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$lxLY-2BERW0kc72bWr7fARmx5Z8
 */
package java.time.temporal;

import java.time.temporal.-$;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public final class _$$Lambda$TemporalAdjusters$lxLY_2BERW0kc72bWr7fARmx5Z8
implements TemporalAdjuster {
    public static final /* synthetic */ -$.Lambda.TemporalAdjusters.lxLY-2BERW0kc72bWr7fARmx5Z8 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$TemporalAdjusters$lxLY_2BERW0kc72bWr7fARmx5Z8();
    }

    private /* synthetic */ _$$Lambda$TemporalAdjusters$lxLY_2BERW0kc72bWr7fARmx5Z8() {
    }

    @Override
    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$firstDayOfNextYear$6(temporal);
    }
}

