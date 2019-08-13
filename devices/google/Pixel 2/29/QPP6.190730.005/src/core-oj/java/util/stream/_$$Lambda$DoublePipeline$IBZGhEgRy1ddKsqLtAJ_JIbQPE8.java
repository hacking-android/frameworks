/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.DoublePipeline;

public final class _$$Lambda$DoublePipeline$IBZGhEgRy1ddKsqLtAJ_JIbQPE8
implements BinaryOperator {
    private final /* synthetic */ BiConsumer f$0;

    public /* synthetic */ _$$Lambda$DoublePipeline$IBZGhEgRy1ddKsqLtAJ_JIbQPE8(BiConsumer biConsumer) {
        this.f$0 = biConsumer;
    }

    @Override
    public final Object apply(Object object, Object object2) {
        return DoublePipeline.lambda$collect$8(this.f$0, object, object2);
    }
}

