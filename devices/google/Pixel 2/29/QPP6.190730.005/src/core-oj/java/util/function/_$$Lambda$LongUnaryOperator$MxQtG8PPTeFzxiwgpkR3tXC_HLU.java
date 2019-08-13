/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.LongUnaryOperator;

public final class _$$Lambda$LongUnaryOperator$MxQtG8PPTeFzxiwgpkR3tXC_HLU
implements LongUnaryOperator {
    private final /* synthetic */ LongUnaryOperator f$0;
    private final /* synthetic */ LongUnaryOperator f$1;

    public /* synthetic */ _$$Lambda$LongUnaryOperator$MxQtG8PPTeFzxiwgpkR3tXC_HLU(LongUnaryOperator longUnaryOperator, LongUnaryOperator longUnaryOperator2) {
        this.f$0 = longUnaryOperator;
        this.f$1 = longUnaryOperator2;
    }

    @Override
    public final long applyAsLong(long l) {
        return LongUnaryOperator.lambda$andThen$1(this.f$0, this.f$1, l);
    }
}

