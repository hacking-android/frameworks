/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$IFopD1JhOBkpZfx3JTKDGTwaQTo
implements Supplier {
    private final /* synthetic */ Collector f$0;

    public /* synthetic */ _$$Lambda$Collectors$IFopD1JhOBkpZfx3JTKDGTwaQTo(Collector collector) {
        this.f$0 = collector;
    }

    public final Object get() {
        return Collectors.lambda$partitioningBy$56(this.f$0);
    }
}

