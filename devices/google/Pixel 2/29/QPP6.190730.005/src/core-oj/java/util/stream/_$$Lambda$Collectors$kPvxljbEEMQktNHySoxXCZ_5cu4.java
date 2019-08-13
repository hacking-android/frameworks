/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$kPvxljbEEMQktNHySoxXCZ_5cu4
implements BiConsumer {
    private final /* synthetic */ ToDoubleFunction f$0;

    public /* synthetic */ _$$Lambda$Collectors$kPvxljbEEMQktNHySoxXCZ_5cu4(ToDoubleFunction toDoubleFunction) {
        this.f$0 = toDoubleFunction;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$averagingDouble$31(this.f$0, (double[])object, object2);
    }
}

