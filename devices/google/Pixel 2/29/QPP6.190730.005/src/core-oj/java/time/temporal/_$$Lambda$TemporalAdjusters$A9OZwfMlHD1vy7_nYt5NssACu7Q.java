/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public final class _$$Lambda$TemporalAdjusters$A9OZwfMlHD1vy7_nYt5NssACu7Q
implements TemporalAdjuster {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$TemporalAdjusters$A9OZwfMlHD1vy7_nYt5NssACu7Q(int n) {
        this.f$0 = n;
    }

    @Override
    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$nextOrSame$10(this.f$0, temporal);
    }
}

