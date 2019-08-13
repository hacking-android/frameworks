/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$DPpNNyj_GqDgBuGvO0w_46Z3Jl8
implements BiConsumer {
    private final /* synthetic */ BiConsumer f$0;
    private final /* synthetic */ Predicate f$1;

    public /* synthetic */ _$$Lambda$Collectors$DPpNNyj_GqDgBuGvO0w_46Z3Jl8(BiConsumer biConsumer, Predicate predicate) {
        this.f$0 = biConsumer;
        this.f$1 = predicate;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$partitioningBy$54(this.f$0, this.f$1, (Collectors.Partition)object, object2);
    }
}

