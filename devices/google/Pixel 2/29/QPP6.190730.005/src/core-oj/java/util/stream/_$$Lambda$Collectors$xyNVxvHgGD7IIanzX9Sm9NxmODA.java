/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.LongSummaryStatistics;
import java.util.function.BiConsumer;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$xyNVxvHgGD7IIanzX9Sm9NxmODA
implements BiConsumer {
    private final /* synthetic */ ToLongFunction f$0;

    public /* synthetic */ _$$Lambda$Collectors$xyNVxvHgGD7IIanzX9Sm9NxmODA(ToLongFunction toLongFunction) {
        this.f$0 = toLongFunction;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$summarizingLong$62(this.f$0, (LongSummaryStatistics)object, object2);
    }
}

