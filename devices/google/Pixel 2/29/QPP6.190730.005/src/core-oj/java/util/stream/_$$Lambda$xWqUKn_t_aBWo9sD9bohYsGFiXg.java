/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;

public final class _$$Lambda$xWqUKn_t_aBWo9sD9bohYsGFiXg
implements Sink.OfDouble {
    private final /* synthetic */ SpinedBuffer.OfDouble f$0;

    public /* synthetic */ _$$Lambda$xWqUKn_t_aBWo9sD9bohYsGFiXg(SpinedBuffer.OfDouble ofDouble) {
        this.f$0 = ofDouble;
    }

    @Override
    public final void accept(double d) {
        this.f$0.accept(d);
    }
}

