/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$chTZg9lp1htTW8rBDwbte7J_EOo
implements Function {
    private final /* synthetic */ Collector f$0;

    public /* synthetic */ _$$Lambda$Collectors$chTZg9lp1htTW8rBDwbte7J_EOo(Collector collector) {
        this.f$0 = collector;
    }

    public final Object apply(Object object) {
        return Collectors.lambda$partitioningBy$57(this.f$0, (Collectors.Partition)object);
    }
}

