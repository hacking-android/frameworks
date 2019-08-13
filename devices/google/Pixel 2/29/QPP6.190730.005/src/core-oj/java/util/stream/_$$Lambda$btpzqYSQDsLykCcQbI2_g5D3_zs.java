/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.Consumer;
import java.util.stream.Sink;

public final class _$$Lambda$btpzqYSQDsLykCcQbI2_g5D3_zs
implements Sink {
    private final /* synthetic */ Consumer f$0;

    public /* synthetic */ _$$Lambda$btpzqYSQDsLykCcQbI2_g5D3_zs(Consumer consumer) {
        this.f$0 = consumer;
    }

    @Override
    public final void accept(Object object) {
        this.f$0.accept(object);
    }
}

