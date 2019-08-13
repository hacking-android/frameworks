/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$PkbZGUBauY6_u1ZrRakcFQjTln0
implements BiConsumer {
    private final /* synthetic */ ToIntFunction f$0;

    public /* synthetic */ _$$Lambda$Collectors$PkbZGUBauY6_u1ZrRakcFQjTln0(ToIntFunction toIntFunction) {
        this.f$0 = toIntFunction;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$summingInt$11(this.f$0, (int[])object, object2);
    }
}

