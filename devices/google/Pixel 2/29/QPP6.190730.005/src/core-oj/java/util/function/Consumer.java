/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Objects;
import java.util.function._$$Lambda$Consumer$fZIgy_f2Fa5seBa8ztxXTExq2p4;

@FunctionalInterface
public interface Consumer<T> {
    public static /* synthetic */ void lambda$andThen$0(Consumer consumer, Consumer consumer2, Object object) {
        consumer.accept(object);
        consumer2.accept(object);
    }

    public void accept(T var1);

    default public Consumer<T> andThen(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        return new _$$Lambda$Consumer$fZIgy_f2Fa5seBa8ztxXTExq2p4(this, consumer);
    }
}

