/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.Consumer;
import java.util.stream.StreamSpliterators;

public final class _$$Lambda$StreamSpliterators$DistinctSpliterator$ojM_Hxa6O4_MX3G2cGvIRG3GI58
implements Consumer {
    private final /* synthetic */ StreamSpliterators.DistinctSpliterator f$0;
    private final /* synthetic */ Consumer f$1;

    public /* synthetic */ _$$Lambda$StreamSpliterators$DistinctSpliterator$ojM_Hxa6O4_MX3G2cGvIRG3GI58(StreamSpliterators.DistinctSpliterator distinctSpliterator, Consumer consumer) {
        this.f$0 = distinctSpliterator;
        this.f$1 = consumer;
    }

    public final void accept(Object object) {
        this.f$0.lambda$forEachRemaining$0$StreamSpliterators$DistinctSpliterator(this.f$1, object);
    }
}

