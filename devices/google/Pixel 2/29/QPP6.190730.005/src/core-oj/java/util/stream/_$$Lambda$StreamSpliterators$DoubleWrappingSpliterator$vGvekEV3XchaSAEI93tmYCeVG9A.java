/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BooleanSupplier;
import java.util.stream.StreamSpliterators;

public final class _$$Lambda$StreamSpliterators$DoubleWrappingSpliterator$vGvekEV3XchaSAEI93tmYCeVG9A
implements BooleanSupplier {
    private final /* synthetic */ StreamSpliterators.DoubleWrappingSpliterator f$0;

    public /* synthetic */ _$$Lambda$StreamSpliterators$DoubleWrappingSpliterator$vGvekEV3XchaSAEI93tmYCeVG9A(StreamSpliterators.DoubleWrappingSpliterator doubleWrappingSpliterator) {
        this.f$0 = doubleWrappingSpliterator;
    }

    @Override
    public final boolean getAsBoolean() {
        return this.f$0.lambda$initPartialTraversalState$0$StreamSpliterators$DoubleWrappingSpliterator();
    }
}

