/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Objects;
import java.util.function._$$Lambda$IntConsumer$Zkqv8_f6uSuSHCYm5dVGj2OCUKA;

@FunctionalInterface
public interface IntConsumer {
    public static /* synthetic */ void lambda$andThen$0(IntConsumer intConsumer, IntConsumer intConsumer2, int n) {
        intConsumer.accept(n);
        intConsumer2.accept(n);
    }

    public void accept(int var1);

    default public IntConsumer andThen(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return new _$$Lambda$IntConsumer$Zkqv8_f6uSuSHCYm5dVGj2OCUKA(this, intConsumer);
    }
}

