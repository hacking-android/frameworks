/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.IntConsumer;
import java.util.stream.Sink;

public final class _$$Lambda$C9lt_0Cg_SARhdNFJsMyHSsCsGA
implements Sink.OfInt {
    private final /* synthetic */ IntConsumer f$0;

    public /* synthetic */ _$$Lambda$C9lt_0Cg_SARhdNFJsMyHSsCsGA(IntConsumer intConsumer) {
        this.f$0 = intConsumer;
    }

    @Override
    public final void accept(int n) {
        this.f$0.accept(n);
    }
}

