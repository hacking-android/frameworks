/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$eESkXUxzUQd_kZxyXI8noD7gpIw
implements Function {
    private final /* synthetic */ Supplier f$0;

    public /* synthetic */ _$$Lambda$Collectors$eESkXUxzUQd_kZxyXI8noD7gpIw(Supplier supplier) {
        this.f$0 = supplier;
    }

    public final Object apply(Object object) {
        return Collectors.lambda$groupingByConcurrent$48(this.f$0, object);
    }
}

