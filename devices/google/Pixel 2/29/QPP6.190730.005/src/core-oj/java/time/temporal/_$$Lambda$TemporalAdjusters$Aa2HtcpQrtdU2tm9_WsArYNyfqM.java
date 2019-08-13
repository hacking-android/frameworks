/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public final class _$$Lambda$TemporalAdjusters$Aa2HtcpQrtdU2tm9_WsArYNyfqM
implements TemporalAdjuster {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$TemporalAdjusters$Aa2HtcpQrtdU2tm9_WsArYNyfqM(int n) {
        this.f$0 = n;
    }

    @Override
    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$next$9(this.f$0, temporal);
    }
}

