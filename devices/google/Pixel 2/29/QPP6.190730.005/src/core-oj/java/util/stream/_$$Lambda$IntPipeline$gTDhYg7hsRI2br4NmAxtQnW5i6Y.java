/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.IntPipeline;

public final class _$$Lambda$IntPipeline$gTDhYg7hsRI2br4NmAxtQnW5i6Y
implements BinaryOperator {
    private final /* synthetic */ BiConsumer f$0;

    public /* synthetic */ _$$Lambda$IntPipeline$gTDhYg7hsRI2br4NmAxtQnW5i6Y(BiConsumer biConsumer) {
        this.f$0 = biConsumer;
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return IntPipeline.lambda$collect$5(this.f$0, object, object2);
    }
}

