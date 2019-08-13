/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Objects;
import java.util.function.Consumer;

public interface Iterator<E> {
    default public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        while (this.hasNext()) {
            consumer.accept(this.next());
        }
    }

    public boolean hasNext();

    public E next();

    default public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}

