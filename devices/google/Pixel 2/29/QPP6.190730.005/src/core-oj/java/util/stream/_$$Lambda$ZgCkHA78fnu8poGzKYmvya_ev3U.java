/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;

public final class _$$Lambda$ZgCkHA78fnu8poGzKYmvya_ev3U
implements Sink.OfInt {
    private final /* synthetic */ SpinedBuffer.OfInt f$0;

    public /* synthetic */ _$$Lambda$ZgCkHA78fnu8poGzKYmvya_ev3U(SpinedBuffer.OfInt ofInt) {
        this.f$0 = ofInt;
    }

    @Override
    public final void accept(int n) {
        this.f$0.accept(n);
    }
}

