/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.IntUnaryOperator;

public final class _$$Lambda$IntUnaryOperator$HBwqtJWwVkNFC818pCzqJ5KBLm0
implements IntUnaryOperator {
    private final /* synthetic */ IntUnaryOperator f$0;
    private final /* synthetic */ IntUnaryOperator f$1;

    public /* synthetic */ _$$Lambda$IntUnaryOperator$HBwqtJWwVkNFC818pCzqJ5KBLm0(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
        this.f$0 = intUnaryOperator;
        this.f$1 = intUnaryOperator2;
    }

    @Override
    public final int applyAsInt(int n) {
        return IntUnaryOperator.lambda$compose$0(this.f$0, this.f$1, n);
    }
}

