/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$AfO_bLozmdhHTtbBN7DysDzpfYM
implements BiConsumer {
    private final /* synthetic */ Function f$0;
    private final /* synthetic */ Supplier f$1;
    private final /* synthetic */ BiConsumer f$2;

    public /* synthetic */ _$$Lambda$Collectors$AfO_bLozmdhHTtbBN7DysDzpfYM(Function function, Supplier supplier, BiConsumer biConsumer) {
        this.f$0 = function;
        this.f$1 = supplier;
        this.f$2 = biConsumer;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$groupingByConcurrent$49(this.f$0, this.f$1, this.f$2, (ConcurrentMap)object, object2);
    }
}

