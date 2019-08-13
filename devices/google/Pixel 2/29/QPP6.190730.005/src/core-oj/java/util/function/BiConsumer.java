/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Objects;
import java.util.function._$$Lambda$BiConsumer$V89VXFfSN6jmL_aAoQrZCMiBju4;

@FunctionalInterface
public interface BiConsumer<T, U> {
    public static /* synthetic */ void lambda$andThen$0(BiConsumer biConsumer, BiConsumer biConsumer2, Object object, Object object2) {
        biConsumer.accept(object, object2);
        biConsumer2.accept(object, object2);
    }

    public void accept(T var1, U var2);

    default public BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> biConsumer) {
        Objects.requireNonNull(biConsumer);
        return new _$$Lambda$BiConsumer$V89VXFfSN6jmL_aAoQrZCMiBju4(this, biConsumer);
    }
}

