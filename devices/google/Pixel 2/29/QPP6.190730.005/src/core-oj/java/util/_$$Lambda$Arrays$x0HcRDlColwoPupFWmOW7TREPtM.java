/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.function.IntToDoubleFunction;

public final class _$$Lambda$Arrays$x0HcRDlColwoPupFWmOW7TREPtM
implements IntConsumer {
    private final /* synthetic */ double[] f$0;
    private final /* synthetic */ IntToDoubleFunction f$1;

    public /* synthetic */ _$$Lambda$Arrays$x0HcRDlColwoPupFWmOW7TREPtM(double[] arrd, IntToDoubleFunction intToDoubleFunction) {
        this.f$0 = arrd;
        this.f$1 = intToDoubleFunction;
    }

    @Override
    public final void accept(int n) {
        Arrays.lambda$parallelSetAll$3(this.f$0, this.f$1, n);
    }
}

