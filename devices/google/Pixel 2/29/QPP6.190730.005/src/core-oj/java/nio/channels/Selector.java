/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

public abstract class Selector
implements Closeable {
    protected Selector() {
    }

    public static Selector open() throws IOException {
        return SelectorProvider.provider().openSelector();
    }

    @Override
    public abstract void close() throws IOException;

    public abstract boolean isOpen();

    public abstract Set<SelectionKey> keys();

    public abstract SelectorProvider provider();

    public abstract int select() throws IOException;

    public abstract int select(long var1) throws IOException;

    public abstract int selectNow() throws IOException;

    public abstract Set<SelectionKey> selectedKeys();

    public abstract Selector wakeup();
}

