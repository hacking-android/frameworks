/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.IntSummaryStatistics;
import java.util.function.BiConsumer;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$49j2hRW8u6KMoxsVt77YSpMRb1g
implements BiConsumer {
    private final /* synthetic */ ToIntFunction f$0;

    public /* synthetic */ _$$Lambda$Collectors$49j2hRW8u6KMoxsVt77YSpMRb1g(ToIntFunction toIntFunction) {
        this.f$0 = toIntFunction;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$summarizingInt$60(this.f$0, (IntSummaryStatistics)object, object2);
    }
}

