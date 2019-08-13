/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.UnaryOperator;

public final class _$$Lambda$TemporalAdjusters$CLbEgdXQzFe17bbP1cAR86Ccar4
implements TemporalAdjuster {
    private final /* synthetic */ UnaryOperator f$0;

    public /* synthetic */ _$$Lambda$TemporalAdjusters$CLbEgdXQzFe17bbP1cAR86Ccar4(UnaryOperator unaryOperator) {
        this.f$0 = unaryOperator;
    }

    @Override
    public final Temporal adjustInto(Temporal temporal) {
        return TemporalAdjusters.lambda$ofDateAdjuster$0(this.f$0, temporal);
    }
}

