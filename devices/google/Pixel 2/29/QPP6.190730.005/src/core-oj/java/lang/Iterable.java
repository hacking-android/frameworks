/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public interface Iterable<T> {
    default public void forEach(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        Iterator<T> iterator = this.iterator();
        while (iterator.hasNext()) {
            consumer.accept(iterator.next());
        }
    }

    public Iterator<T> iterator();

    default public Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(this.iterator(), 0);
    }
}

