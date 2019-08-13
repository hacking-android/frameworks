/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class _$$Lambda$ConcurrentMap$T12JRbgGLhxGbYCuTfff6_dTrMk
implements BiConsumer {
    private final /* synthetic */ ConcurrentMap f$0;
    private final /* synthetic */ BiFunction f$1;

    public /* synthetic */ _$$Lambda$ConcurrentMap$T12JRbgGLhxGbYCuTfff6_dTrMk(ConcurrentMap concurrentMap, BiFunction biFunction) {
        this.f$0 = concurrentMap;
        this.f$1 = biFunction;
    }

    public final void accept(Object object, Object object2) {
        ConcurrentMap.lambda$replaceAll$0(this.f$0, this.f$1, object, object2);
    }
}

