/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

public interface DirectoryStream<T>
extends Closeable,
Iterable<T> {
    @Override
    public Iterator<T> iterator();

    @FunctionalInterface
    public static interface Filter<T> {
        public boolean accept(T var1) throws IOException;
    }

}

