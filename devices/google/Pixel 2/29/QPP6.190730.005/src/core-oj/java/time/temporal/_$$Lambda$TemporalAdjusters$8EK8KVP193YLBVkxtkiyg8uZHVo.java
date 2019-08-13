/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.temporal.-$
 *  java.time.temporal.-$$Lambda
 *  java.time.temporal.-$$Lambda$TemporalAdjusters
 *  java.time.temporal.-$$Lambda$TemporalAdjusters$8EK8KVP193YLBVkxtkiyg8uZHVo
 */
package java.time.temporal;

import java.time.temporal.-$;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public final class _$$Lambda$TemporalAdjusters$8EK8KVP193YLBVkxtkiyg8uZHVo
implements TemporalAdjuster {
    public static final /* synthetic */ -$.Lambda.TemporalAdjusters.8EK8KVP193YLBVkxtkiyg8uZHVo INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$TemporalAdjusters$8EK8KVP193YLBVkxtkiyg8uZHVo();
    }

    private /* synthetic */ _$$Lambda$TemporalAdjusters$8EK8KVP193YLBVkxtkiyg8uZHVo() {
    }

    @Override
    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$firstDayOfMonth$1(temporal);
    }
}

