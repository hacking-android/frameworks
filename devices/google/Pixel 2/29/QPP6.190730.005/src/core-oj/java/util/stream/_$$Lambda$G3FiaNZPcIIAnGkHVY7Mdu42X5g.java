/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.LongConsumer;
import java.util.stream.Sink;

public final class _$$Lambda$G3FiaNZPcIIAnGkHVY7Mdu42X5g
implements Sink.OfLong {
    private final /* synthetic */ LongConsumer f$0;

    public /* synthetic */ _$$Lambda$G3FiaNZPcIIAnGkHVY7Mdu42X5g(LongConsumer longConsumer) {
        this.f$0 = longConsumer;
    }

    @Override
    public final void accept(long l) {
        this.f$0.accept(l);
    }
}

