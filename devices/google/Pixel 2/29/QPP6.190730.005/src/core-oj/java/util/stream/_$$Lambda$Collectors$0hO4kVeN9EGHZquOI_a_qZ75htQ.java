/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.DoubleSummaryStatistics;
import java.util.function.BiConsumer;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$0hO4kVeN9EGHZquOI_a_qZ75htQ
implements BiConsumer {
    private final /* synthetic */ ToDoubleFunction f$0;

    public /* synthetic */ _$$Lambda$Collectors$0hO4kVeN9EGHZquOI_a_qZ75htQ(ToDoubleFunction toDoubleFunction) {
        this.f$0 = toDoubleFunction;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$summarizingDouble$64(this.f$0, (DoubleSummaryStatistics)object, object2);
    }
}

