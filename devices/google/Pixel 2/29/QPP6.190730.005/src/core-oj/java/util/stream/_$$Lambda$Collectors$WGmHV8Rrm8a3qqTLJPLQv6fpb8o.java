/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$WGmHV8Rrm8a3qqTLJPLQv6fpb8o
implements Function {
    private final /* synthetic */ Supplier f$0;

    public /* synthetic */ _$$Lambda$Collectors$WGmHV8Rrm8a3qqTLJPLQv6fpb8o(Supplier supplier) {
        this.f$0 = supplier;
    }

    public final Object apply(Object object) {
        return Collectors.lambda$groupingByConcurrent$50(this.f$0, object);
    }
}

