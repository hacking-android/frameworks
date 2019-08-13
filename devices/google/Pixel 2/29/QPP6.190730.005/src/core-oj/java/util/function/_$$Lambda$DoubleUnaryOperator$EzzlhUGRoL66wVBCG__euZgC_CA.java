/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.DoubleUnaryOperator;

public final class _$$Lambda$DoubleUnaryOperator$EzzlhUGRoL66wVBCG__euZgC_CA
implements DoubleUnaryOperator {
    private final /* synthetic */ DoubleUnaryOperator f$0;
    private final /* synthetic */ DoubleUnaryOperator f$1;

    public /* synthetic */ _$$Lambda$DoubleUnaryOperator$EzzlhUGRoL66wVBCG__euZgC_CA(DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2) {
        this.f$0 = doubleUnaryOperator;
        this.f$1 = doubleUnaryOperator2;
    }

    @Override
    public final double applyAsDouble(double d) {
        return DoubleUnaryOperator.lambda$andThen$1(this.f$0, this.f$1, d);
    }
}

