/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;

public final class _$$Lambda$Arrays$KFf05FUz26CqVc_cf2bKY9C927o
implements IntConsumer {
    private final /* synthetic */ int[] f$0;
    private final /* synthetic */ IntUnaryOperator f$1;

    public /* synthetic */ _$$Lambda$Arrays$KFf05FUz26CqVc_cf2bKY9C927o(int[] arrn, IntUnaryOperator intUnaryOperator) {
        this.f$0 = arrn;
        this.f$1 = intUnaryOperator;
    }

    @Override
    public final void accept(int n) {
        Arrays.lambda$parallelSetAll$1(this.f$0, this.f$1, n);
    }
}

