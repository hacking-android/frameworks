/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;

public final class _$$Lambda$6BdNjvJJOqgXMfHsEogzyrab_60
implements Sink.OfLong {
    private final /* synthetic */ SpinedBuffer.OfLong f$0;

    public /* synthetic */ _$$Lambda$6BdNjvJJOqgXMfHsEogzyrab_60(SpinedBuffer.OfLong ofLong) {
        this.f$0 = ofLong;
    }

    @Override
    public final void accept(long l) {
        this.f$0.accept(l);
    }
}

