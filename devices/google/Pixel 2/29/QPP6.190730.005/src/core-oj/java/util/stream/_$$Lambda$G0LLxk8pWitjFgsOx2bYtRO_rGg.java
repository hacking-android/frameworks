/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.DoubleConsumer;
import java.util.stream.Sink;

public final class _$$Lambda$G0LLxk8pWitjFgsOx2bYtRO_rGg
implements DoubleConsumer {
    private final /* synthetic */ Sink f$0;

    public /* synthetic */ _$$Lambda$G0LLxk8pWitjFgsOx2bYtRO_rGg(Sink sink) {
        this.f$0 = sink;
    }

    @Override
    public final void accept(double d) {
        this.f$0.accept(d);
    }
}

