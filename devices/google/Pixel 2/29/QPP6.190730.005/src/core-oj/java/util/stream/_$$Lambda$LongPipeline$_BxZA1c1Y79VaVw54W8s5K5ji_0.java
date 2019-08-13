/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.LongPipeline;

public final class _$$Lambda$LongPipeline$_BxZA1c1Y79VaVw54W8s5K5ji_0
implements BinaryOperator {
    private final /* synthetic */ BiConsumer f$0;

    public /* synthetic */ _$$Lambda$LongPipeline$_BxZA1c1Y79VaVw54W8s5K5ji_0(BiConsumer biConsumer) {
        this.f$0 = biConsumer;
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return LongPipeline.lambda$collect$5(this.f$0, object, object2);
    }
}

