/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BooleanSupplier;
import java.util.stream.StreamSpliterators;

public final class _$$Lambda$StreamSpliterators$WrappingSpliterator$Ky6g3CKkCccuRWAvbAL1cAsdkNk
implements BooleanSupplier {
    private final /* synthetic */ StreamSpliterators.WrappingSpliterator f$0;

    public /* synthetic */ _$$Lambda$StreamSpliterators$WrappingSpliterator$Ky6g3CKkCccuRWAvbAL1cAsdkNk(StreamSpliterators.WrappingSpliterator wrappingSpliterator) {
        this.f$0 = wrappingSpliterator;
    }

    @Override
    public final boolean getAsBoolean() {
        return this.f$0.lambda$initPartialTraversalState$0$StreamSpliterators$WrappingSpliterator();
    }
}

