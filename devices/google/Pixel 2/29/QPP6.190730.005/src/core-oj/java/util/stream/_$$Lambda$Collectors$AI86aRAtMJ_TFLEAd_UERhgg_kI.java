/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$AI86aRAtMJ_TFLEAd_UERhgg_kI
implements BiConsumer {
    private final /* synthetic */ ToLongFunction f$0;

    public /* synthetic */ _$$Lambda$Collectors$AI86aRAtMJ_TFLEAd_UERhgg_kI(ToLongFunction toLongFunction) {
        this.f$0 = toLongFunction;
    }

    public final void accept(Object object, Object object2) {
        Collectors.lambda$averagingLong$27(this.f$0, (long[])object, object2);
    }
}

