/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$vmLceJDpkkH4HVeqPcL08DnO8yg
implements BiConsumer {
    private final /* synthetic */ BiConsumer f$0;
    private final /* synthetic */ Function f$1;

    public /* synthetic */ _$$Lambda$Collectors$vmLceJDpkkH4HVeqPcL08DnO8yg(BiConsumer biConsumer, Function function) {
        this.f$0 = biConsumer;
        this.f$1 = function;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$mapping$8(this.f$0, this.f$1, object, object2);
    }
}

