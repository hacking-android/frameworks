/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$jDXjrt99im0xRBROpkCAqoLkqR4
implements BiConsumer {
    private final /* synthetic */ ToLongFunction f$0;

    public /* synthetic */ _$$Lambda$Collectors$jDXjrt99im0xRBROpkCAqoLkqR4(ToLongFunction toLongFunction) {
        this.f$0 = toLongFunction;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$summingLong$15(this.f$0, (long[])object, object2);
    }
}

