/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.IntUnaryOperator;

public final class _$$Lambda$IntUnaryOperator$F5l8VUrgGacAzg6VKzynJCfDBx4
implements IntUnaryOperator {
    private final /* synthetic */ IntUnaryOperator f$0;
    private final /* synthetic */ IntUnaryOperator f$1;

    public /* synthetic */ _$$Lambda$IntUnaryOperator$F5l8VUrgGacAzg6VKzynJCfDBx4(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
        this.f$0 = intUnaryOperator;
        this.f$1 = intUnaryOperator2;
    }

    @Override
    public final int applyAsInt(int n) {
        return IntUnaryOperator.lambda$andThen$1(this.f$0, this.f$1, n);
    }
}

