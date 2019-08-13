/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$zuotCFMPpEd_pFOpcqCzvWNwmcE
implements BiConsumer {
    private final /* synthetic */ ToDoubleFunction f$0;

    public /* synthetic */ _$$Lambda$Collectors$zuotCFMPpEd_pFOpcqCzvWNwmcE(ToDoubleFunction toDoubleFunction) {
        this.f$0 = toDoubleFunction;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$summingDouble$19(this.f$0, (double[])object, object2);
    }
}

