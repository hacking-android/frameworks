/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$5sBFPk12YoFTd83smSoPj46DB_A
implements BiConsumer {
    private final /* synthetic */ ToIntFunction f$0;

    public /* synthetic */ _$$Lambda$Collectors$5sBFPk12YoFTd83smSoPj46DB_A(ToIntFunction toIntFunction) {
        this.f$0 = toIntFunction;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$averagingInt$23(this.f$0, (long[])object, object2);
    }
}

