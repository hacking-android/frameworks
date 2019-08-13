/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BooleanSupplier;
import java.util.stream.StreamSpliterators;

public final class _$$Lambda$StreamSpliterators$IntWrappingSpliterator$js67IRBzuEwtfp5Z3OTF_GfmUTw
implements BooleanSupplier {
    private final /* synthetic */ StreamSpliterators.IntWrappingSpliterator f$0;

    public /* synthetic */ _$$Lambda$StreamSpliterators$IntWrappingSpliterator$js67IRBzuEwtfp5Z3OTF_GfmUTw(StreamSpliterators.IntWrappingSpliterator intWrappingSpliterator) {
        this.f$0 = intWrappingSpliterator;
    }

    @Override
    public final boolean getAsBoolean() {
        return this.f$0.lambda$initPartialTraversalState$0$StreamSpliterators$IntWrappingSpliterator();
    }
}

