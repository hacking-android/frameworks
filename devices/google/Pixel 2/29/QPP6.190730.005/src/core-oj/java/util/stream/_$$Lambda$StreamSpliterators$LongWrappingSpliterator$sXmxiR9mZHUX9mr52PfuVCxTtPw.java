/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BooleanSupplier;
import java.util.stream.StreamSpliterators;

public final class _$$Lambda$StreamSpliterators$LongWrappingSpliterator$sXmxiR9mZHUX9mr52PfuVCxTtPw
implements BooleanSupplier {
    private final /* synthetic */ StreamSpliterators.LongWrappingSpliterator f$0;

    public /* synthetic */ _$$Lambda$StreamSpliterators$LongWrappingSpliterator$sXmxiR9mZHUX9mr52PfuVCxTtPw(StreamSpliterators.LongWrappingSpliterator longWrappingSpliterator) {
        this.f$0 = longWrappingSpliterator;
    }

    @Override
    public final boolean getAsBoolean() {
        return this.f$0.lambda$initPartialTraversalState$0$StreamSpliterators$LongWrappingSpliterator();
    }
}

