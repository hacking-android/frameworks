/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

public final class _$$Lambda$Arrays$H0YqaggIxZUqId4_BJ1BLcUa93k
implements IntConsumer {
    private final /* synthetic */ Object[] f$0;
    private final /* synthetic */ IntFunction f$1;

    public /* synthetic */ _$$Lambda$Arrays$H0YqaggIxZUqId4_BJ1BLcUa93k(Object[] arrobject, IntFunction intFunction) {
        this.f$0 = arrobject;
        this.f$1 = intFunction;
    }

    @Override
    public final void accept(int n) {
        Arrays.lambda$parallelSetAll$0(this.f$0, this.f$1, n);
    }
}

