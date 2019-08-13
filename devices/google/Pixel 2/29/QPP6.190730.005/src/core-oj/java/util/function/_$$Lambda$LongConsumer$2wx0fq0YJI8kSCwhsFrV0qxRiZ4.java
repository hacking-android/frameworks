/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.LongConsumer;

public final class _$$Lambda$LongConsumer$2wx0fq0YJI8kSCwhsFrV0qxRiZ4
implements LongConsumer {
    private final /* synthetic */ LongConsumer f$0;
    private final /* synthetic */ LongConsumer f$1;

    public /* synthetic */ _$$Lambda$LongConsumer$2wx0fq0YJI8kSCwhsFrV0qxRiZ4(LongConsumer longConsumer, LongConsumer longConsumer2) {
        this.f$0 = longConsumer;
        this.f$1 = longConsumer2;
    }

    @Override
    public final void accept(long l) {
        LongConsumer.lambda$andThen$0(this.f$0, this.f$1, l);
    }
}

