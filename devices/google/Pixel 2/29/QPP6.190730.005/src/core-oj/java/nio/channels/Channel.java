/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.Closeable;
import java.io.IOException;

public interface Channel
extends Closeable {
    @Override
    public void close() throws IOException;

    public boolean isOpen();
}

