/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Iterator;
import java.util.Spliterator;

public interface BaseStream<T, S extends BaseStream<T, S>>
extends AutoCloseable {
    @Override
    public void close();

    public boolean isParallel();

    public Iterator<T> iterator();

    public S onClose(Runnable var1);

    public S parallel();

    public S sequential();

    public Spliterator<T> spliterator();

    public S unordered();
}

