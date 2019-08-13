/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;

public interface Closeable
extends AutoCloseable {
    @Override
    public void close() throws IOException;
}

