/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.IntConsumer;
import java.util.stream.Sink;

public final class _$$Lambda$wDsxx48ovPSGeNEb3P6H9u7YX0k
implements IntConsumer {
    private final /* synthetic */ Sink f$0;

    public /* synthetic */ _$$Lambda$wDsxx48ovPSGeNEb3P6H9u7YX0k(Sink sink) {
        this.f$0 = sink;
    }

    @Override
    public final void accept(int n) {
        this.f$0.accept(n);
    }
}

