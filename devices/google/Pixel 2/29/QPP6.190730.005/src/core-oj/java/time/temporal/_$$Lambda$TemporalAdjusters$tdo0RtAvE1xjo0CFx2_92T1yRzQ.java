/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public final class _$$Lambda$TemporalAdjusters$tdo0RtAvE1xjo0CFx2_92T1yRzQ
implements TemporalAdjuster {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$TemporalAdjusters$tdo0RtAvE1xjo0CFx2_92T1yRzQ(int n, int n2) {
        this.f$0 = n;
        this.f$1 = n2;
    }

    @Override
    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$dayOfWeekInMonth$7(this.f$0, this.f$1, temporal);
    }
}

