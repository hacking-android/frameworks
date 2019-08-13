/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.DoubleConsumer;
import java.util.stream.Sink;

public final class _$$Lambda$fgFAI1gk0hw2h3IP9CmHWlY3YkM
implements Sink.OfDouble {
    private final /* synthetic */ DoubleConsumer f$0;

    public /* synthetic */ _$$Lambda$fgFAI1gk0hw2h3IP9CmHWlY3YkM(DoubleConsumer doubleConsumer) {
        this.f$0 = doubleConsumer;
    }

    @Override
    public final void accept(double d) {
        this.f$0.accept(d);
    }
}

