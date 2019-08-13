/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Objects;
import java.util.function._$$Lambda$LongConsumer$2wx0fq0YJI8kSCwhsFrV0qxRiZ4;

@FunctionalInterface
public interface LongConsumer {
    public static /* synthetic */ void lambda$andThen$0(LongConsumer longConsumer, LongConsumer longConsumer2, long l) {
        longConsumer.accept(l);
        longConsumer2.accept(l);
    }

    public void accept(long var1);

    default public LongConsumer andThen(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        return new _$$Lambda$LongConsumer$2wx0fq0YJI8kSCwhsFrV0qxRiZ4(this, longConsumer);
    }
}

