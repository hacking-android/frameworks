/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.function.IntToLongFunction;

public final class _$$Lambda$Arrays$aBSX_SvA5f2Q1t8_MODHDGhokzk
implements IntConsumer {
    private final /* synthetic */ long[] f$0;
    private final /* synthetic */ IntToLongFunction f$1;

    public /* synthetic */ _$$Lambda$Arrays$aBSX_SvA5f2Q1t8_MODHDGhokzk(long[] arrl, IntToLongFunction intToLongFunction) {
        this.f$0 = arrl;
        this.f$1 = intToLongFunction;
    }

    @Override
    public final void accept(int n) {
        Arrays.lambda$parallelSetAll$2(this.f$0, this.f$1, n);
    }
}

